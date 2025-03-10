package com.apu.apstay.staff.security.models.inputmodels;

import com.apu.apstay.enums.CommuteMode;

/**
 *
 * @author alexc
 */
public class SecurityProfileInputModel {
    
    private CommuteMode commuteMode;

    public CommuteMode getCommuteMode() {
        return commuteMode;
    }

    public void setCommuteMode(CommuteMode commuteMode) {
        this.commuteMode = commuteMode;
    }
}
