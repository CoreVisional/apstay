package com.apu.apstay.staff.manager.models.inputmodels;

import jakarta.validation.constraints.NotNull;

/**
 *
 * @author alexc
 */
public class RegistrationApproveInputModel {
    
    @NotNull
    private Long registrationId;
    
    @NotNull
    private Long reviewerId;

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
}
