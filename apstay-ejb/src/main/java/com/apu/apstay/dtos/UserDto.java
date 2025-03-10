package com.apu.apstay.dtos;

import java.time.LocalDateTime;
import java.util.Set;

/**
 *
 * @author alexc
 */
public record UserDto(
        Long id,
        String username,
        String email,
        String passwordHash,
        Set<RoleDto> roles,
        boolean isActive,
        boolean isLocked,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime modifiedAt,
        Long modifiedBy
) { }
