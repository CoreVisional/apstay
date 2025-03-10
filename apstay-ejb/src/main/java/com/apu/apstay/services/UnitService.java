package com.apu.apstay.services;

import com.apu.apstay.services.common.BaseService;
import com.apu.apstay.commands.units.UnitCreateCommand;
import com.apu.apstay.commands.units.UnitUpdateCommand;
import com.apu.apstay.dtos.UnitDto;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.facades.UnitFacade;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;
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
    
    public List<UnitDto> getAvailableUnits() {
        return unitFacade.findAll().stream()
                .filter(unit -> !unit.isOccupied())
                .map(unitFactory::toDto)
                .collect(Collectors.toList());
    }

    public UnitDto createUnit(UnitCreateCommand command) throws BusinessRulesException {
        validateUnitName(command.unitName(), null);
        var _unit = unitFactory.getNew();
        _unit.setUnitName(command.unitName());
        _unit.setFloorNumber(command.floorNumber());
        setAuditFields(_unit);
        unitFacade.create(_unit);
        return unitFactory.toDto(_unit);
    }

    public UnitDto updateUnit(Long id, UnitUpdateCommand command) throws BusinessRulesException {
        var _unit = unitFacade.find(id);
        if (_unit == null) {
            throw new BusinessRulesException("Unit not found!");
        }
        validateUnitName(command.unitName(), id);
        _unit.setUnitName(command.unitName());
        _unit.setFloorNumber(command.floorNumber());
        _unit.setOccupied(command.occupied());
        setAuditFields(_unit);
        unitFacade.edit(_unit);
        return unitFactory.toDto(_unit);
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
