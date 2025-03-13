package com.apu.apstay.staff.manager.models.inputmodels;

import com.apu.apstay.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 *
 * @author alexc
 */
public class ResidentEditInputModel {
    
    @NotBlank(message = "Username must not be blank!")
    private String username;
    
    @NotBlank(message = "Email address must not be blank!")
    private String email;
    
    @NotNull(message = "Unit assignment is required!")
    private Long unitId;

    @NotBlank(message = "Full Name must be provided!")
    private String fullName;
    
    @NotBlank(message = "Identity Number must be provided!")
    private String identityNumber;
    
    @NotNull(message = "Select a gender!")
    private Gender gender;

    @NotBlank(message = "Contact number must be provided!")
    private String phone;
    
    @NotBlank(message = "Address must not be blank!")
    private String address;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
