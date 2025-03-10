package com.apu.apstay.resident.models.inputmodels;

import com.apu.apstay.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author alexc
 */
public class VisitRequestInputModel {
    
    // Visitor Information
    @NotBlank(message = "Visitor name must not be blank!")
    private String name;
    
    @NotBlank(message = "Identity number must not be blank!")
    private String identityNumber;

    @NotNull(message = "Gender must not be blank!")
    private Gender gender;
    
    @NotBlank(message = "Phone number must not be blank!")
    private String phone;
    
    // Visit Request Information
    @NotBlank(message = "Select when this visit is scheduled")
    private String arrivalDateTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(String arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }
}
