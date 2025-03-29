package com.apu.apstay.services;

import com.apu.apstay.dtos.RoleDto;
import com.apu.apstay.facades.RoleFacade;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author alexc
 */
@Stateless
public class RoleService {
    
    @EJB
    private RoleFacade roleFacade;
    
    @EJB
    private RoleFactory roleFactory;
    
    // <editor-fold defaultstate="collapsed" desc="Implementations of RoleService">
    public List<RoleDto> getStaffRoles() {
        return roleFacade.findAll().stream()
                .filter(role -> role.getName().equals("manager") || role.getName().equals("security"))
                .map(roleFactory::toDto)
                .collect(Collectors.toList());
    }
    
    public List<RoleDto> getAssignableRoles(boolean isSuperuser) {
        var _assignableRoles = getStaffRoles();
        if (isSuperuser) {
            boolean hasSuperuser = _assignableRoles.stream()
                    .anyMatch(role -> role.name().equalsIgnoreCase("superuser"));
            if (!hasSuperuser) {
                var superuserRole = roleFacade.findAll().stream()
                        .filter(role -> role.getName().equalsIgnoreCase("superuser"))
                        .map(roleFactory::toDto)
                        .findFirst()
                        .orElse(null);
                if (superuserRole != null) {
                    _assignableRoles.add(superuserRole);
                }
            }
        }
        return _assignableRoles;
    }

    public boolean hasRole(Set<RoleDto> userRoles, String roleName) {
        return userRoles.stream()
                .anyMatch(role -> role.name().equalsIgnoreCase(roleName));
    }
    // </editor-fold>
}
