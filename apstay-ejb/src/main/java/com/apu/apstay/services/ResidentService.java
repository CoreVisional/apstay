package com.apu.apstay.services;

import com.apu.apstay.dtos.ResidentDto;
import com.apu.apstay.entities.Resident;
import com.apu.apstay.facades.ResidentFacade;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author alexc
 */
@Stateless
public class ResidentService {
    
    @EJB
    private ResidentFactory residentFactory;
    
    @EJB
    private ResidentFacade residentFacade;
    
    public Resident getById(Long id) {
        return residentFacade.find(id);
    }
    
    public Long getByUserId(Long userId) {
        var resident = residentFacade.findByUserId(userId);
        return resident != null ? resident.getId() : null;
    }
    
    public List<ResidentDto> getAll() {
        return residentFacade.findAll().stream()
                .map(residentFactory::toDto)
                .collect(Collectors.toList());
    }
}
