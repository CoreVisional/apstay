package com.apu.apstay.entities;

import com.apu.apstay.entities.common.BaseModel;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author alexc
 */
@Entity
public class User extends BaseModel {
    
    // <editor-fold defaultstate="collapsed" desc="Properties">
    @NotBlank
    @Column(nullable = false, unique = true)
    private String username;
    
    @NotBlank
    @Email(message = "Please provide a valid email address.")
    @Column(nullable = false, unique = true)
    private String email;
    
    @ManyToMany
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
    
    @OneToOne(mappedBy = "user")
    private UserProfile userProfile;
    
    @OneToMany(mappedBy = "registrant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AccountRegistration> accountRegistrations = new HashSet<>();
    
    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private boolean active = true;

    @Column(nullable = false)
    private boolean locked = false;
    
    @Column(nullable = false)
    private int failedLoginAttempts = 0;
    
    @Column(nullable = true)
    private LocalDateTime lastFailedLogin;
    
    @Column(nullable = true)
    private LocalDateTime lastLogin;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    public User() {
    }

    public User(String username, String email, UserProfile userProfile, String passwordHash, LocalDateTime lastFailedLogin, LocalDateTime lastLogin) {
        this.username = username;
        this.email = email;
        this.userProfile = userProfile;
        this.passwordHash = passwordHash;
        this.lastFailedLogin = lastFailedLogin;
        this.lastLogin = lastLogin;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public Set<AccountRegistration> getAccountRegistrations() {
        return accountRegistrations;
    }

    public void setAccountRegistrations(Set<AccountRegistration> accountRegistrations) {
        this.accountRegistrations = accountRegistrations;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    public LocalDateTime getLastFailedLogin() {
        return lastFailedLogin;
    }

    public void setLastFailedLogin(LocalDateTime lastFailedLogin) {
        this.lastFailedLogin = lastFailedLogin;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
    // </editor-fold>
    
    @Override
    public String toString() {
        return "User [id=" + getId() + ", username=" + username + ", email=" + email + "]";
    }
}
