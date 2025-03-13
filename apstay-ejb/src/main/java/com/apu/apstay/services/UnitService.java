package com.apu.apstay.services;

import com.apu.apstay.services.common.BaseService;
import com.apu.apstay.commands.units.UnitCreateCommand;
import com.apu.apstay.commands.units.UnitUpdateCommand;
import com.apu.apstay.dtos.UnitDto;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.facades.UnitFacade;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
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
public class UnitService extends BaseService {

    @EJB
    private UnitFacade unitFacade;

    @EJB
    private UnitFactory unitFactory;

    // <editor-fold defaultstate="collapsed" desc="Implementations of UnitService">
    public UnitDto getById(Long id) {
        return unitFactory.toDto(unitFacade.find(id));
    }
    
    public List<UnitDto> getAll() {
        return unitFacade.findAll().stream()
                .map(unitFactory::toDto)
                .collect(Collectors.toList());
    }

    public UnitDto createUnit(UnitCreateCommand command) throws BusinessRulesException {
        validateUnitName(command.unitName(), null);
        var unit = unitFactory.getNew();
        unit.setUnitName(command.unitName());
        unit.setFloorNumber(command.floorNumber());
        setAuditFields(unit);
        unitFacade.create(unit);
        return unitFactory.toDto(unit);
    }

    public UnitDto updateUnit(Long id, UnitUpdateCommand command) throws BusinessRulesException {
        var unit = unitFacade.find(id);
        if (unit == null) {
            throw new BusinessRulesException("Unit not found");
        }

        validateUnitName(command.unitName(), id);
        unit.setUnitName(command.unitName());
        unit.setFloorNumber(command.floorNumber());
        unit.setCapacity(command.capacity());
        unit.setActive(command.active());
        setAuditFields(unit);
        unitFacade.edit(unit);
        return unitFactory.toDto(unit);
    }

    public List<UnitDto> getAvailableUnits() {
        return unitFacade.findAllWithAvailableSpace().stream()
                .map(unitFactory::toDto)
                .collect(Collectors.toList());
    }
    
    public boolean deleteUnit(Long id) {
        var unit = unitFacade.find(id);

        if (unit.getResidents() != null && !unit.getResidents().isEmpty()) {
            return false;
        }

        unitFacade.remove(unit);
        return true;
    }
    
    public Map<String, Object> getUnitOccupancyReportData() {
        Map<String, Object> reportData = new HashMap<>();

        int totalUnits = (int) unitFacade.count();
        int activeUnits = unitFacade.countActiveUnits();
        int inactiveUnits = unitFacade.countInactiveUnits();
        int fullyOccupiedUnits = unitFacade.countFullyOccupiedUnits();
        int vacantUnits = unitFacade.countVacantUnits();

        reportData.put("totalUnits", totalUnits);
        reportData.put("activeUnits", activeUnits);
        reportData.put("inactiveUnits", inactiveUnits);
        reportData.put("fullyOccupiedUnits", fullyOccupiedUnits);
        reportData.put("vacantUnits", vacantUnits);

        Map<String, Object> occupancyChartData = new HashMap<>();
        int occupiedUnits = activeUnits - vacantUnits;
        List<Integer> occupancyValues = Arrays.asList(occupiedUnits, vacantUnits);
        occupancyChartData.put("values", occupancyValues);
        reportData.put("occupancyChartData", occupancyChartData);

        List<Object[]> floorData = unitFacade.getOccupancyRateByFloor();
        Map<String, Object> floorOccupancyData = new HashMap<>();

        List<String> floorLabels = new ArrayList<>();
        List<Double> floorValues = new ArrayList<>();

        for (Object[] row : floorData) {
            int floorNumber = ((Number) row[0]).intValue();
            int occupied = ((Number) row[1]).intValue();
            int total = ((Number) row[2]).intValue();

            floorLabels.add("Floor " + floorNumber);

            double occupancyRate = total > 0 ? (occupied * 100.0 / total) : 0.0;
            floorValues.add(occupancyRate);
        }

        floorOccupancyData.put("labels", floorLabels);
        floorOccupancyData.put("values", floorValues);
        reportData.put("floorOccupancyData", floorOccupancyData);

        List<Object[]> capacityData = unitFacade.getUnitCapacityDistribution();
        Map<String, Object> capacityDistributionData = new HashMap<>();

        List<String> capacityLabels = new ArrayList<>();
        List<Integer> capacityValues = new ArrayList<>();

        for (Object[] row : capacityData) {
            int capacity = ((Number) row[0]).intValue();
            int count = ((Number) row[1]).intValue();

            String label = capacity == 1 ? "1 Person" : 
                          capacity == 2 ? "2 People" : 
                          capacity == 3 ? "3 People" : 
                          capacity + "+ People";

            capacityLabels.add(label);
            capacityValues.add(count);
        }

        capacityDistributionData.put("labels", capacityLabels);
        capacityDistributionData.put("values", capacityValues);
        reportData.put("capacityDistributionData", capacityDistributionData);

        return reportData;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Implementations">
    private void validateUnitName(String unitName, Long currentUnitId) throws BusinessRulesException {
        var existingUnit = unitFacade.findByUnitName(unitName);
        if (existingUnit != null && (currentUnitId == null || !existingUnit.getId().equals(currentUnitId))) {
            throw new BusinessRulesException("unitName", "Unit name already exists!");
        }
    }
    // </editor-fold>
}
