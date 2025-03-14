package com.apu.apstay.staff.manager.models.viewmodels.staff;

import com.apu.apstay.enums.CommuteMode;
import com.apu.apstay.enums.Gender;

/**
 *
 * @author alexc
 */
public class SecurityDetailsViewModel extends StaffDetailsViewModel {
    
    private final CommuteMode commuteMode;
    
    public SecurityDetailsViewModel(Long id, String name, String identityNumber, 
                                   String email, String role, Gender gender, 
                                   String phone, String address, CommuteMode commuteMode) {
        super(id, name, identityNumber, email, role, gender, phone, address);
        this.commuteMode = commuteMode;
    }
    
    public CommuteMode getCommuteMode() {
        return commuteMode;
    }
}
