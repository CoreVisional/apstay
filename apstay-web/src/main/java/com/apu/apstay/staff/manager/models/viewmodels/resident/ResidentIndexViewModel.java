package com.apu.apstay.staff.manager.models.viewmodels.resident;

/**
 *
 * @author alexc
 */
public record ResidentIndexViewModel(
        Long id,
        String fullName,
        String unitName,
        int floorNumber
) { }
