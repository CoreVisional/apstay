package com.apu.apstay.common.models.viewmodels;

import com.apu.apstay.enums.Gender;

/**
 *
 * @author alexc
 */
public class UserProfileViewModel {

    private final String name;
    private final String identityNumber;
    private final Gender gender;
    private final String phone;
    private final String address;

    public UserProfileViewModel(String name, String identityNumber, Gender gender, String phone, String address) {
        this.name = name;
        this.identityNumber = identityNumber;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }
}
