package com.apu.apstay.staff.manager.models.viewmodels.registration;

import com.apu.apstay.enums.ApprovalStatus;
import java.time.LocalDateTime;

/**
 *
 * @author alexc
 */
public record AccountRegistrationDetailsViewModel (
        Long id,
        Long unitId,
        String name,
        String reviewerName,
        String identityNumber,
        String email,
        String gender,
        String phone,
        String address,
        String unitName,
        ApprovalStatus status,
        String remarks,
        LocalDateTime createdAt
) {}

