package com.apu.apstay.services;

import com.apu.apstay.dtos.VisitRequestDto;
import com.apu.apstay.entities.VisitRequest;
import jakarta.ejb.Stateless;

/**
 *
 * @author alexc
 */
@Stateless
public class VisitRequestFactory {
    
    public VisitRequest getNew() {
        return new VisitRequest();
    }
    
    public VisitRequestDto toDto(VisitRequest entity) {
        if (entity == null) {
            return null;
        }
        
        var residentId = entity.getResident() != null ? entity.getResident().getId() : null;
        var visitorId = entity.getVisitor() != null ? entity.getVisitor().getId() : null;
        
        return new VisitRequestDto(
                entity.getId(),
                residentId,
                visitorId,
                entity.getArrivalDateTime(),
                entity.getStatus(),
                entity.isActive()
        );
    }
}
