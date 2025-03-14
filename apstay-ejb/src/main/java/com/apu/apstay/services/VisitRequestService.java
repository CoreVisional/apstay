package com.apu.apstay.services;

import com.apu.apstay.commands.visits.VisitRequestCreateCommand;
import com.apu.apstay.dtos.VisitRequestDto;
import com.apu.apstay.entities.VisitRequest;
import com.apu.apstay.enums.VisitRequestStatus;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.facades.ResidentFacade;
import com.apu.apstay.facades.VisitCodeFacade;
import com.apu.apstay.facades.VisitRequestFacade;
import com.apu.apstay.facades.VisitorFacade;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author alexc
 */
@Stateless
public class VisitRequestService {
    
    @EJB
    private VisitRequestFacade visitRequestFacade;
    
    @EJB
    private VisitorFacade visitorFacade;
    
    @EJB
    private VisitRequestFactory visitRequestFactory;
    
    @EJB
    private ResidentFacade residentFacade;
    
    @EJB
    private VisitorFactory visitorFactory;
    
    @EJB
    private VisitCodeFacade visitCodeFacade;
    
    @EJB
    private VisitCodeFactory visitCodeFactory;
    
    public VisitRequestDto getById(Long id) {
        var visitRequest = visitRequestFacade.find(id);
        if (visitRequest == null) {
            return null;
        }
        return visitRequestFactory.toDto(visitRequest);
    }

    public List<VisitRequestDto> getAll() {
        return visitRequestFacade.findAll().stream()
                .map(visitRequestFactory::toDto)
                .collect(Collectors.toList());
    }
    
    public List<VisitRequest> getAllWithDetails() {
        return visitRequestFacade.findAllWithDetails();
    }
    
    public List<VisitRequestDto> getAllByResidentId(Long residentId) {
        var resident = residentFacade.find(residentId);
        if (resident == null) {
            return List.of();
        }
        
        var requests = visitRequestFacade.findByResident(resident);
        return requests.stream()
                .map(visitRequestFactory::toDto)
                .collect(Collectors.toList());
    }

    
    public VisitRequestDto create(VisitRequestCreateCommand command) throws BusinessRulesException {

        var visitor = visitorFacade.findByIdentityNumber(command.identityNumber());
        
        if (visitor == null) {
            visitor = visitorFactory.getNew();
            visitor.setName(command.name());
            visitor.setIdentityNumber(command.identityNumber());
            visitor.setGender(command.gender());
            visitor.setPhone(command.phone());

            visitorFacade.create(visitor);
        }
        
        var resident = residentFacade.findByUserId(command.residentId());

        var visitRequest = visitRequestFactory.getNew();
        visitRequest.setVisitor(visitor);
        visitRequest.setResident(resident);
        
        var arrivalDateTime = LocalDateTime.parse(command.arrivalDateTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        visitRequest.setArrivalDateTime(arrivalDateTime);
        visitRequest.setStatus(VisitRequestStatus.SUBMITTED);
        visitRequestFacade.create(visitRequest);
        
        var code = generateVerificationCode();
        var visitCode = visitCodeFactory.getNew();
        visitCode.setVisitRequest(visitRequest);
        visitCode.setCode(code);
        visitCodeFacade.create(visitCode);
        
        return visitRequestFactory.toDto(visitRequest);
    }
    
    public VisitRequestDto changeStatus(Long id, VisitRequestStatus status) {
        
        var _visitRequest = visitRequestFacade.find(id);
        
        if (_visitRequest == null) {
            return null;
        }
        
        _visitRequest.setStatus(status);
        
        if (status == VisitRequestStatus.CANCELLED || status == VisitRequestStatus.CLOSED) {
            _visitRequest.setActive(false);
        }
        
        visitRequestFacade.edit(_visitRequest);
        
        return visitRequestFactory.toDto(_visitRequest);
    }
    
    private String generateVerificationCode() {
        var random = new SecureRandom();
        var code = random.ints(6, 0, 10)
                            .mapToObj(String::valueOf)
                            .collect(Collectors.joining());
        return visitCodeFacade.findByCode(code) == null ? 
               code : generateVerificationCode();
    }
    
    public Map<String, Object> getVisitAnalysisData() {
        Map<String, Object> reportData = new HashMap<>();

        int totalRequests = visitRequestFacade.countVisitRequests();
        int reachedRequests = visitRequestFacade.countVisitRequestsByStatus(VisitRequestStatus.REACHED);
        int submittedRequests = visitRequestFacade.countVisitRequestsByStatus(VisitRequestStatus.SUBMITTED);
        int cancelledRequests = visitRequestFacade.countVisitRequestsByStatus(VisitRequestStatus.CANCELLED);

        reportData.put("totalRequests", totalRequests);
        reportData.put("reachedRequests", reachedRequests);
        reportData.put("submittedRequests", submittedRequests);
        reportData.put("cancelledRequests", cancelledRequests);

        List<Integer> statusDistribution = Arrays.asList(
            reachedRequests,
            submittedRequests,
            cancelledRequests
        );
        reportData.put("statusDistribution", statusDistribution);

        List<Object[]> dayOfWeekData = visitRequestFacade.getVisitRequestsByDayOfWeek();
        int[] visitsByDay = new int[7];

        Arrays.fill(visitsByDay, 0);

        for (Object[] dayData : dayOfWeekData) {
            int dayIndex = ((Number) dayData[0]).intValue();
            int count = ((Number) dayData[1]).intValue();

            int adjustedIndex = (dayIndex + 5) % 7;
            visitsByDay[adjustedIndex] = count;
        }

        List<String> dayLabels = Arrays.asList(
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );

        List<Integer> dayData = new ArrayList<>();
        for (int count : visitsByDay) {
            dayData.add(count);
        }

        reportData.put("dayLabels", dayLabels);
        reportData.put("dayData", dayData);

        List<Object[]> mostVisitedUnitsData = visitRequestFacade.getMostVisitedUnits(5);
        List<Map<String, Object>> mostVisitedUnits = new ArrayList<>();

        for (Object[] unitData : mostVisitedUnitsData) {
            Map<String, Object> unit = new HashMap<>();

            String unitName = (String) unitData[0];
            int floorNumber = ((Number) unitData[1]).intValue();
            int totalVisits = ((Number) unitData[2]).intValue();
            int unitReachedVisits = ((Number) unitData[3]).intValue();
            int unitCancelledVisits = ((Number) unitData[4]).intValue();

            double percentageOfTotal = totalRequests > 0 ? 
                    (totalVisits * 100.0 / totalRequests) : 0;

            unit.put("unitName", unitName);
            unit.put("floorNumber", floorNumber);
            unit.put("totalVisits", totalVisits);
            unit.put("reachedVisits", unitReachedVisits);
            unit.put("cancelledVisits", unitCancelledVisits);
            unit.put("percentageOfTotal", percentageOfTotal);

            mostVisitedUnits.add(unit);
        }

        reportData.put("mostVisitedUnits", mostVisitedUnits);

        return reportData;
    }
}
