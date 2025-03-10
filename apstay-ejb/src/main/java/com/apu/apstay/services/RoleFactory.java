package com.apu.apstay.services;

import com.apu.apstay.dtos.RoleDto;
import com.apu.apstay.entities.Role;
import jakarta.ejb.Stateless;

/**
 *
 * @author alexc
 */
@Stateless
public class RoleFactory {

    public RoleDto toDto(Role entity) {
        if (entity == null) {
            return null;
        }
        return new RoleDto(
                entity.getId(), 
                entity.getName(), 
                entity.getCreatedAt(), 
                entity.getCreatedBy(), 
                entity.getModifiedAt(), 
                entity.getModifiedBy()
        );
    }
}
