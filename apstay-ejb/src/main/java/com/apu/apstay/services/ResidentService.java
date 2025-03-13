package com.apu.apstay.services;

import com.apu.apstay.commands.users.ResidentUpdateCommand;
import com.apu.apstay.dtos.ResidentDto;
import com.apu.apstay.entities.Resident;
import com.apu.apstay.exceptions.BusinessRulesException;
import com.apu.apstay.facades.ResidentFacade;
import com.apu.apstay.facades.UnitFacade;
import com.apu.apstay.facades.UserFacade;
import com.apu.apstay.facades.UserProfileFacade;
import com.apu.apstay.services.common.BaseService;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author alexc
 */
@Stateless
public class ResidentService extends BaseService {
    
    @EJB
    private ResidentFactory residentFactory;
    
    @EJB
    private ResidentFacade residentFacade;
    
    @EJB
    private UnitFacade unitFacade;
    
    @EJB
    private UserFacade userFacade;
    
    @EJB
    private UserProfileFacade userProfileFacade;
    
    public Resident getById(Long id) {
        return residentFacade.find(id);
    }
    
    public Long getByUserId(Long userId) {
        var resident = residentFacade.findByUserId(userId);
        return resident != null ? resident.getId() : null;
    }
    
    public List<ResidentDto> getAll() {
        return residentFacade.findAll().stream()
                .map(residentFactory::toDto)
                .collect(Collectors.toList());
    }
    
    public void assignUnit(Long residentId, Long unitId) {
        var resident = residentFacade.find(residentId);
        var unit = unitFacade.find(unitId);

        int occupancy = unit.getResidents() != null ? unit.getResidents().size() : 0;
        boolean hasSpace = occupancy < unit.getCapacity();

        if (hasSpace) {
            resident.setUnit(unit);
            if (unit.getResidents() == null) {
                unit.setResidents(new HashSet<>());
            }
            unit.getResidents().add(resident);

            setAuditFields(resident);
            setAuditFields(unit);

            residentFacade.edit(resident);
            unitFacade.edit(unit);
        }
    }
    
    public void updateResident(Long residentId, ResidentUpdateCommand command) throws BusinessRulesException {
        
        var resident = residentFacade.find(residentId);
        if (resident == null || resident.getUser() == null) {
            throw new BusinessRulesException("Resident not found");
        }

        var user = resident.getUser();
        var userProfile = user.getUserProfile();

        var existingUsername = userFacade.findByUsername(command.username());
        if (existingUsername != null && !existingUsername.getId().equals(user.getId())) {
            throw new BusinessRulesException("username", "Username is already taken!");
        }

        var existingEmail = userFacade.findByEmail(command.email());
        if (existingEmail != null && !existingEmail.getId().equals(user.getId())) {
            throw new BusinessRulesException("email", "Email is already registered!");
        }

        user.setUsername(command.username());
        user.setEmail(command.email());
        setAuditFields(user);
        userFacade.edit(user);

        if (userProfile != null) {
            if (command.identityNumber() != null && !command.identityNumber().isEmpty()) {
                var existingProfile = userProfileFacade.findByIdentityNumber(command.identityNumber());
                if (existingProfile != null && !existingProfile.getId().equals(userProfile.getId())) {
                    throw new BusinessRulesException("identityNumber", "Identity number is already registered!");
                }
            }

            userProfile.setName(command.fullName());
            userProfile.setIdentityNumber(command.identityNumber());
            userProfile.setGender(command.gender());
            userProfile.setPhone(command.phone());
            userProfile.setAddress(command.address());
            setAuditFields(userProfile);
            userProfileFacade.edit(userProfile);
        }

        var currentUnit = resident.getUnit();
        if (currentUnit == null || !currentUnit.getId().equals(command.unitId())) {
            if (currentUnit != null && currentUnit.getResidents() != null) {
                currentUnit.getResidents().remove(resident);
                setAuditFields(currentUnit);
                unitFacade.edit(currentUnit);
            }

            var newUnit = unitFacade.find(command.unitId());

            int occupancy = newUnit.getResidents() != null ? newUnit.getResidents().size() : 0;
            boolean hasSpace = occupancy < newUnit.getCapacity();

            if (!hasSpace) {
                throw new BusinessRulesException("unitId", "Selected unit is at full capacity");
            }

            resident.setUnit(newUnit);

            if (newUnit.getResidents() == null) {
                newUnit.setResidents(new HashSet<>());
            }
            newUnit.getResidents().add(resident);

            setAuditFields(newUnit);
            unitFacade.edit(newUnit);
        }

        setAuditFields(resident);
        residentFacade.edit(resident);
    }
    
    public void deactivateResident(Long residentId) {
        var resident = residentFacade.find(residentId);
        if (resident == null || resident.getUser() == null) {
            return;
        }

        var user = resident.getUser();
        user.setActive(false);
        setAuditFields(user);
        userFacade.edit(user);

        var unit = resident.getUnit();
        if (unit != null && unit.getResidents() != null) {
            unit.getResidents().remove(resident);
            setAuditFields(unit);
            unitFacade.edit(unit);
        }
    }
    
    public void reactivateResident(Long residentId, Long unitId) {
        var resident = residentFacade.find(residentId);
        if (resident == null || resident.getUser() == null) {
            return;
        }

        var unit = unitFacade.find(unitId);
        if (unit == null) {
            return;
        }

        int occupancy = unit.getResidents() != null ? unit.getResidents().size() : 0;
        boolean hasSpace = occupancy < unit.getCapacity();

        if (!hasSpace) {
            return;
        }

        var user = resident.getUser();
        user.setActive(true);
        setAuditFields(user);
        userFacade.edit(user);

        resident.setUnit(unit);

        if (unit.getResidents() == null) {
            unit.setResidents(new HashSet<>());
        }
        unit.getResidents().add(resident);

        setAuditFields(resident);
        residentFacade.edit(resident);
        unitFacade.edit(unit);
    }
}
