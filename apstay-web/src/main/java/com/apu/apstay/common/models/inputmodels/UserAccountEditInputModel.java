package com.apu.apstay.common.models.inputmodels;

import jakarta.validation.constraints.NotBlank;

/**
 *
 * @author alexc
 */
public class UserAccountEditInputModel {
    
    @NotBlank(message = "Username cannot be blank!")
    private String username;
    
    @NotBlank(message = "Email address cannot be blank!")
    private String email;

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
}
