package com.apu.apstay.staff.manager.models.viewmodels.visitrequest;

import com.apu.apstay.enums.VisitRequestStatus;
import java.time.LocalDateTime;

/**
 *
 * @author alexc
 */
public record VisitRequestIndexViewModel (
        String visitorName,
        String residentName,
        String unit,
        LocalDateTime arrivalDateTime,
        VisitRequestStatus status
) {

    public String statusClass() {
        return switch (status) {
            case REACHED -> "success";
            case SUBMITTED -> "warning";
            case CANCELLED -> "danger";
            case CLOSED -> "secondary";
            default -> "primary";
        };
    }
}
