package com.apu.apstay.services;

import com.apu.apstay.dtos.SecurityDto;
import com.apu.apstay.entities.Security;
import com.apu.apstay.enums.CommuteMode;
import com.apu.apstay.facades.SecurityFacade;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

/**
 *
 * @author alexc
 */
@Stateless
public class SecurityService {
    
    @EJB
    private SecurityFacade securityFacade;
    
    @EJB
    private SecurityFactory securityFactory;
    
    public Security getById(Long id) {
        return securityFacade.find(id);
    }
    
    public SecurityDto getByUserId(Long userId) {
        var security = securityFacade.findByUserId(userId);
        return security != null ? securityFactory.toDto(security) : null;
    }
    
    public void updateCommuteMode(Long userId, CommuteMode commuteMode) {
        var security = securityFacade.findByUserId(userId);
        security.setCommuteMode(commuteMode);
        securityFacade.edit(security);
    }
}
