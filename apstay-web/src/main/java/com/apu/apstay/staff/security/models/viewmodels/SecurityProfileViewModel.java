package com.apu.apstay.staff.security.models.viewmodels;

import com.apu.apstay.common.models.viewmodels.UserProfileViewModel;
import com.apu.apstay.enums.CommuteMode;
import com.apu.apstay.enums.Gender;

/**
 *
 * @author alexc
 */
public class SecurityProfileViewModel extends UserProfileViewModel {
    
    private final CommuteMode commuteMode;

    public SecurityProfileViewModel(String name, String identityNumber, Gender gender, String phone, String address, CommuteMode commuteMode) {
        super(name, identityNumber, gender, phone, address);
        this.commuteMode = commuteMode;
    }

    public CommuteMode getCommuteMode() {
        return commuteMode;
    }
}
