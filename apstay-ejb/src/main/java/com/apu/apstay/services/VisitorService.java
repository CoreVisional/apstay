package com.apu.apstay.services;

import com.apu.apstay.dtos.VisitorDto;
import com.apu.apstay.facades.VisitorFacade;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;

/**
 *
 * @author alexc
 */
@Stateless
public class VisitorService {
    
    @EJB
    private VisitorFacade visitorFacade;
    
    @EJB
    private VisitorFactory visitorFactory;
    
    public VisitorDto getById(Long id) {
        var _visitor = visitorFacade.find(id);
        return visitorFactory.toDto(_visitor);
    }
    
    public String getNameById(Long id) {
        var _visitor = visitorFacade.find(id);
        return _visitor != null ? _visitor.getName() : "Unknown";
    }
}
