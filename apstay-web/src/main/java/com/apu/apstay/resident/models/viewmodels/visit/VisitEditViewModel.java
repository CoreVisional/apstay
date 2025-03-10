package com.apu.apstay.resident.models.viewmodels.visit;

import com.apu.apstay.enums.VisitRequestStatus;
import java.time.LocalDateTime;

/**
 *
 * @author alexc
 */
public record VisitEditViewModel (
        Long id,
        String name,
        String verificationCode,
        LocalDateTime arrivalDateTime,
        VisitRequestStatus status
) { }
