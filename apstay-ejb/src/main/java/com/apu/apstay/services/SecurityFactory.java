package com.apu.apstay.services;

import com.apu.apstay.dtos.SecurityDto;
import com.apu.apstay.entities.Security;
import jakarta.ejb.Stateless;

/**
 *
 * @author alexc
 */
@Stateless
public class SecurityFactory {
    
    public SecurityDto toDto(Security entity) {
        if (entity == null) {
            return null;
        }
        return new SecurityDto(
                entity.getCommuteMode()
        );
    }
}
