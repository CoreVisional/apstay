package com.apu.apstay.services;

import com.apu.apstay.commands.visits.VisitRequestCreateCommand;
import com.apu.apstay.dtos.VisitRequestDto;
import com.apu.apstay.entities.VisitCode;
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
import java.util.List;
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
}
