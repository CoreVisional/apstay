package com.apu.apstay.staff.manager.models.viewmodels.staff;

import com.apu.apstay.enums.Gender;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author alexc
 */
public class StaffDetailsViewModel {
    
    private final Long id;
    private final String name;
    private final String identityNumber;
    private final String email;
    private final String role;
    private final Gender gender;
    private final String phone;
    private final String address;
    
    public StaffDetailsViewModel(Long id, String name, String identityNumber, 
                               String email, String role, Gender gender, 
                               String phone, String address) {
        this.id = id;
        this.name = name;
        this.identityNumber = identityNumber;
        this.email = email;
        this.role = role;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getIdentityNumber() {
        return identityNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getRole() {
        return StringUtils.capitalize(role);
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
