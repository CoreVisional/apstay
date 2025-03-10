package com.apu.apstay.common.models.inputmodels;

import com.apu.apstay.enums.Gender;
import jakarta.validation.constraints.NotBlank;

/**
 *
 * @author alexc
 */
public class UserProfileEditInputModel {
    
    @NotBlank(message = "Name cannot be blank!")
    private String name;
    
    @NotBlank(message = "Identity number cannot be blank!")
    private String identityNumber;
    private Gender gender;
    
    @NotBlank(message = "Phone number cannot be blank!")
    private String phone;
    
    @NotBlank(message = "Address cannot be blank!")
    private String address;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
