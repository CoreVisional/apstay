package com.apu.apstay.staff.manager.models.viewmodels.staff;

import com.apu.apstay.dtos.UserDto;
import com.apu.apstay.dtos.UserProfileDto;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author alexc
 */
public class StaffIndexViewModel {
    private final Long id;
    private final String username;
    private final String email;
    private final String fullName;
    private final boolean isActive;
    private final String role;
    
    private StaffIndexViewModel(UserDto user, UserProfileDto profile) {
        this.id = user.id();
        this.username = user.username();
        this.email = user.email();
        this.fullName = profile != null ? profile.name() : "";
        this.isActive = user.isActive();
        this.role = user.roles().stream()
                .map(r -> StringUtils.capitalize(r.name()))
                .collect(Collectors.joining(", "));
    }
    
    public static List<StaffIndexViewModel> from(List<UserDto> users, List<UserProfileDto> profiles) {
        return users.stream()
                .map(user -> {
                    var profile = profiles.stream()
                            .filter(p -> p.userId().equals(user.id()))
                            .findFirst()
                            .orElse(null);
                    return new StaffIndexViewModel(user, profile);
                })
                .collect(Collectors.toList());
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public boolean isActive() { return isActive; }
    public String getRole() { return role; }
}