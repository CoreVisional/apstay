package com.apu.apstay.security;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 *
 * @author alexc
 */
@Named
@SessionScoped
public class SessionContext implements Serializable {
    private Long currentUserId;
    
    public Long getCurrentUserId() {
        return currentUserId;
    }
    
    public void setCurrentUserId(Long userId) {
        this.currentUserId = userId;
    }
    
    public boolean isAuthenticated() {
        return currentUserId != null;
    }
    
    public void logout() {
        this.currentUserId = null;
    }
}
