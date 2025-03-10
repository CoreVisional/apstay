package com.apu.apstay.staff.manager.models.viewmodels.registration;

import com.apu.apstay.enums.ApprovalStatus;
import java.time.LocalDateTime;

/**
 *
 * @author alexc
 */
public record AccountRegistrationIndexViewModel(
        Long id,
        String name,
        LocalDateTime createdAt,
        ApprovalStatus status
) { }
