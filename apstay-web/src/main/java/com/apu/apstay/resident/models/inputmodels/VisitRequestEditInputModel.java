package com.apu.apstay.resident.models.inputmodels;

import com.apu.apstay.enums.VisitRequestStatus;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author alexc
 */
public class VisitRequestEditInputModel {
    
    @NotNull(message = "Select a status!")
    private VisitRequestStatus status;

    public VisitRequestStatus getStatus() {
        return status;
    }

    public void setStatus(VisitRequestStatus status) {
        this.status = status;
    }
}
