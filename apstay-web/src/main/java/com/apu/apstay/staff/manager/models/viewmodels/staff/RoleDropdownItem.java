package com.apu.apstay.staff.manager.models.viewmodels.staff;

import com.apu.apstay.dtos.RoleDto;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author alexc
 */
public class RoleDropdownItem {
    
    // <editor-fold defaultstate="collapsed" desc="Variables">
    private final Long id;
    private final String name;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    private RoleDropdownItem(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    // </editor-fold>
    
    public static List<RoleDropdownItem> fromList(List<RoleDto> dtos) {
        return dtos.stream()
            .map(dto -> new RoleDropdownItem(dto.id(), dto.name()))
            .collect(Collectors.toList());
    }
    
    // <editor-fold defaultstate="collapsed" desc="Getters">
    public Long getId() { return id; }
    public String getName() { return StringUtils.capitalize(name); }
    // </editor-fold>
}
