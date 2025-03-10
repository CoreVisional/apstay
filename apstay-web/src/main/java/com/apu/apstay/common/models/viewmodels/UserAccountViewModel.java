package com.apu.apstay.common.models.viewmodels;

/**
 *
 * @author alexc
 */
public class UserAccountViewModel {
    
    private final String username;
    private final String email;

    public UserAccountViewModel(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
