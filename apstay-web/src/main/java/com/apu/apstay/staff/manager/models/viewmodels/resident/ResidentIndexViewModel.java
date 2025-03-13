package com.apu.apstay.staff.manager.models.viewmodels.resident;

import com.apu.apstay.staff.manager.models.viewmodels.unit.UnitViewModel;
import java.util.List;

/**
 *
 * @author alexc
 */
public record ResidentIndexViewModel(
        Long id,
        List<UnitViewModel> availableUnits,
        String fullName,
        String unitName,
        int floorNumber,
        boolean active
) { }
