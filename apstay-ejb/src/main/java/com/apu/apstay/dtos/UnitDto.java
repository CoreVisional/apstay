package com.apu.apstay.dtos;

import java.time.LocalDateTime;

/**
 *
 * @author alexc
 */
public record UnitDto (
        Long id,
        String unitName,
        int floorNumber,
        boolean occupied,
        boolean active,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime modifiedAt,
        Long modifiedBy
) {}