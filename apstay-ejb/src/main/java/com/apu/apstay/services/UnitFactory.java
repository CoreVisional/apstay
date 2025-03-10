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

        return new UnitDto(
                entity.getId(),
                entity.getUnitName(),
                entity.getFloorNumber(),
                entity.isOccupied(),
                entity.isActive(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }
}
