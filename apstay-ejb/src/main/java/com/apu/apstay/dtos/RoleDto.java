package com.apu.apstay.dtos;

import java.time.LocalDateTime;

/**
 *
 * @author alexc
 */
public record RoleDto(
        Long id,
        String name,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime modifiedAt,
        Long modifiedBy
) {}
