package com.apu.apstay.services;

import com.apu.apstay.dtos.VisitCodeDto;
import com.apu.apstay.entities.VisitCode;
import jakarta.ejb.Stateless;

/**
 *
 * @author alexc
 */
@Stateless
public class VisitCodeFactory {
    
    public VisitCode getNew() {
        return new VisitCode();
    }
    
    public VisitCodeDto toDto(VisitCode entity) {
        if (entity == null) {
            return null;
        }
        
        return new VisitCodeDto(
            entity.getId(),
            entity.getVisitRequest() != null ? entity.getVisitRequest().getId() : null,
            entity.getCode(),
            entity.isActive()
        );
    }
}
