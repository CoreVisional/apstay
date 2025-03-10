package com.apu.apstay.commands.visits;

import com.apu.apstay.enums.Gender;

/**
 *
 * @author alexc
 */
public record VisitRequestCreateCommand(
        Long residentId,
        String arrivalDateTime,
        String name,
        String identityNumber,
        Gender gender,
        String phone
) { }
