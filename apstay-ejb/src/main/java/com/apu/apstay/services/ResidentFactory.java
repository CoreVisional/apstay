package com.apu.apstay.services;

import com.apu.apstay.dtos.ResidentDto;
import com.apu.apstay.entities.Resident;
import jakarta.ejb.Stateless;

/**
 *
 * @author alexc
 */
@Stateless
public class ResidentFactory {

    public Resident getNew() {
        return new Resident();
    }
    
    public ResidentDto toDto(Resident entity) {
        if (entity == null) {
            return null;
        }

        var fullName = entity.getUser().getUserProfile() != null ? entity.getUser().getUserProfile().getName() : entity.getUser().getUsername();

        return new ResidentDto(
                entity.getId(),
                fullName,
                entity.getUser().getEmail(),
                entity.getUnit().getUnitName(),
                entity.getUnit().getFloorNumber()
        );
    }
}
