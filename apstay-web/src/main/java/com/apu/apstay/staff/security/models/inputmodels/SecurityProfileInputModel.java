package com.apu.apstay.staff.security.models.inputmodels;

import com.apu.apstay.common.models.inputmodels.UserProfileEditInputModel;
import com.apu.apstay.enums.CommuteMode;
import com.apu.apstay.enums.Gender;

/**
 *
 * @author alexc
 */
public class SecurityProfileInputModel extends UserProfileEditInputModel {
    
    private CommuteMode commuteMode;

    public SecurityProfileInputModel() {
        super();
    }
    
    public SecurityProfileInputModel(String name, String identityNumber, Gender gender, String phone, String address, CommuteMode commuteMode) {
        super(name, identityNumber, gender, phone, address);
        this.commuteMode = commuteMode;
    }
    
    public CommuteMode getCommuteMode() {
        return commuteMode;
    }

    public void setCommuteMode(CommuteMode commuteMode) {
        this.commuteMode = commuteMode;
    }
}
