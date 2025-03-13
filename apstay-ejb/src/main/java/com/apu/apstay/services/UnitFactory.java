package com.apu.apstay.services;

import com.apu.apstay.dtos.UnitDto;
import com.apu.apstay.entities.Unit;
import jakarta.ejb.Stateless;

/**
 *
 * @author alexc
 */
@Stateless
public class UnitFactory {
    
    public Unit getNew() {
        return new Unit();
    }

    public UnitDto toDto(Unit entity) {
        if (entity == null) {
            return null;
        }

        int currentOccupancy = entity.getResidents() != null ? entity.getResidents().size() : 0;
        boolean hasAvailableSpace = currentOccupancy < entity.getCapacity();

        return new UnitDto(
                entity.getId(),
                entity.getUnitName(),
                entity.getFloorNumber(),
                entity.getCapacity(),
                entity.isActive(),
                currentOccupancy,
                hasAvailableSpace,
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }
}
