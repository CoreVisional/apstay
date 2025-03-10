package com.apu.apstay.services;

import com.apu.apstay.dtos.VisitorDto;
import com.apu.apstay.entities.Visitor;
import jakarta.ejb.Stateless;

/**
 *
 * @author alexc
 */
@Stateless
public class VisitorFactory {
    
    public Visitor getNew() {
        return new Visitor();
    }
    
    public VisitorDto toDto(Visitor entity) {
        if (entity == null) {
            return null;
        }
        
        return new VisitorDto(
                entity.getId(),
                entity.getName(),
                entity.getIdentityNumber(),
                entity.getGender(),
                entity.getPhone()
        );
    }
}
