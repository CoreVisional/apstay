package com.apu.apstay.dtos;

import com.apu.apstay.enums.VisitRequestStatus;
import java.time.LocalDateTime;

/**
 *
 * @author alexc
 */
public record VisitRequestDto(
        Long id,
        Long residentId,
        Long visitorId,
        LocalDateTime arrivalDateTime,
        VisitRequestStatus status,
        boolean active
) { }
