package com.apu.apstay.staff.manager.models.viewmodels.resident;

import com.apu.apstay.enums.Gender;
import com.apu.apstay.staff.manager.models.viewmodels.unit.UnitViewModel;
import java.util.List;

/**
 *
 * @author alexc
 */
public record ResidentEditViewModel(
        Long id,
        Long currentUnitId,
        String currentUnitName,
        int currentUnitFloor,
        List<UnitViewModel> availableUnits,
        String username,
        String email,
        String fullName,
        String identityNumber,
        Gender gender,
        String phone,
        String address
) {

    public String currentUnit() {
        if (currentUnitName == null) {
            return "None";
        }
        return currentUnitName + "-" + currentUnitFloor;
    }
}
