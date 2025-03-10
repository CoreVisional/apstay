package com.apu.apstay.services;

import com.apu.apstay.dtos.RoleDto;
import com.apu.apstay.dtos.UserDto;
import com.apu.apstay.entities.Role;
import com.apu.apstay.entities.User;
import jakarta.ejb.Stateless;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author alexc
 */
@Stateless
public class UserFactory {
    
    public User getNew() {
        return new User();
    }
    
    public UserDto toDto(User entity) {
        if (entity == null) {
            return null;
        }
        return new UserDto(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getPasswordHash(),
                mapRoles(entity.getRoles()),
                entity.isActive(),
                entity.isLocked(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy()
        );
    }
    
    private Set<RoleDto> mapRoles(Set<Role> roles) {
        return roles.stream()
                .map(role -> new RoleDto(
                        role.getId(),
                        role.getName(),
                        role.getCreatedAt(),
                        role.getCreatedBy(),
                        role.getModifiedAt(),
                        role.getModifiedBy()
                ))
                .collect(Collectors.toSet());
    }
}
