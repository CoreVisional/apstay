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
        int capacity,
        boolean active,
        int currentOccupancy,
        boolean hasAvailableSpace,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime modifiedAt,
        Long modifiedBy
) {}