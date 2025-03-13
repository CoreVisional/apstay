package com.apu.apstay.services.common;

import com.apu.apstay.entities.common.BaseModel;
import com.apu.apstay.security.SessionContext;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;

/**
 *
 * @author alexc
 */
@Stateless
public abstract class BaseService {
    
    @Inject
    protected SessionContext sessionContext;
    
    protected void setAuditFields(BaseModel entity) {
        Long currentUserId = sessionContext.isAuthenticated() 
            ? sessionContext.getCurrentUserId() 
            : null;
            
        entity.setCreatedBy(currentUserId);
        entity.setModifiedBy(currentUserId);
    }
    
    protected void updateAuditFields(BaseModel entity) {
        Long currentUserId = sessionContext.isAuthenticated() 
            ? sessionContext.getCurrentUserId() 
            : null;
            
        entity.setModifiedBy(currentUserId);
    }
}
