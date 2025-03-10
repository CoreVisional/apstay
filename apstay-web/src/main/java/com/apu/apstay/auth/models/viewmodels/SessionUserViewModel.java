package com.apu.apstay.auth.models.viewmodels;

import com.apu.apstay.dtos.UserDto;
import com.apu.apstay.dtos.UserProfileDto;
import java.util.stream.Collectors;

/**
 *
 * @author alexc
 */
public class SessionUserViewModel {
    private final Long id;
    private final String username;
    private final String email;
    private final String fullName;
    private final String roles;

    public SessionUserViewModel(UserDto user, UserProfileDto profile) {
        this.id = user.id();
        this.username = user.username();
        this.email = user.email();
        this.fullName = profile != null ? profile.name() : "";
        this.roles = user.roles().stream()
                .map(role -> role.name())
                .collect(Collectors.joining(", "));
    }
    
    public boolean hasRole(String roleName) {
        return roles.toLowerCase().contains(roleName.toLowerCase());
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getRoles() { return roles; }
}