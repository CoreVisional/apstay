package com.apu.apstay.staff.manager.models.viewmodels.staff;

import com.apu.apstay.dtos.UserDto;
import com.apu.apstay.dtos.UserProfileDto;
import com.apu.apstay.enums.Gender;
import java.util.stream.Collectors;

/**
 *
 * @author alexc
 */
public class StaffEditViewModel {
    private final Long id;
    private final Long roleId;
    private final String username;
    private final String email;
    private final String fullName;
    private final String phone;
    private final String address;
    private final String identityNumber;
    private final Gender gender;
    private final boolean isActive;
    private final boolean isLocked;
    private final String roles;

    private StaffEditViewModel(UserDto user, UserProfileDto profile) {
        this.id = user.id();
        this.roleId = user.roles().stream()
                .findFirst()
                .map(role -> role.id())
                .orElse(null);
        this.username = user.username();
        this.email = user.email();
        this.fullName = profile != null ? profile.name() : "";
        this.phone = profile != null ? profile.phone() : "";
        this.address = profile != null ? profile.address() : "";
        this.identityNumber = profile != null ? profile.identityNumber() : "";
        this.gender = profile != null ? profile.gender() : null;
        this.isActive = user.isActive();
        this.isLocked = user.isLocked();
        this.roles = user.roles().stream()
                .map(role -> role.name())
                .collect(Collectors.joining(", "));
    }

    public static StaffEditViewModel from(UserDto user, UserProfileDto profile) {
        return new StaffEditViewModel(user, profile);
    }

    public Long getId() { return id; }
    public Long getRoleId() { return roleId; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getIdentityNumber() { return identityNumber; }
    public Gender getGender() { return gender; }
    public boolean isActive() { return isActive; }
    public boolean isLocked() { return isLocked; }
    public String getRoles() { return roles; }
}
