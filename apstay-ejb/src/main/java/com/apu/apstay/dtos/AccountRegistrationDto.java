package com.apu.apstay.dtos;

import com.apu.apstay.enums.ApprovalStatus;
import java.time.LocalDateTime;

/**
 *
 * @author alexc
 */
public record AccountRegistrationDto(
        Long id,
        Long registrant_id,
        Long reviewerId,
        Long unitId,
        String reviewerName,
        String name,
        String identityNumber,
        String email,
        String gender,
        String phone,
        String address,
        String unitName,
        int floorNumber,
        ApprovalStatus status,
        String remarks,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime modifiedAt,
        Long modifiedBy
) {}

