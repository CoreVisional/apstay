package com.apu.apstay.staff.manager.models.inputmodels;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author alexc
 */
public class RegistrationRejectInputModel {
    
    @NotNull
    private Long registrationId;
    
    @NotNull
    private Long reviewerId;
    
    @NotBlank(message = "Remarks must be provided!")
    private String remarks;

    public Long getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }

    public Long getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
