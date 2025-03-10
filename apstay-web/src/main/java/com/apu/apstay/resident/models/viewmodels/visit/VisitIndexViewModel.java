package com.apu.apstay.resident.models.viewmodels.visit;

import com.apu.apstay.enums.VisitRequestStatus;
import java.time.LocalDateTime;

/**
 *
 * @author alexc
 */
public record VisitIndexViewModel (
        Long id,
        String name,
        String verificationCode,
        LocalDateTime arrivalDateTime,
        VisitRequestStatus status,
        boolean active
) { }
