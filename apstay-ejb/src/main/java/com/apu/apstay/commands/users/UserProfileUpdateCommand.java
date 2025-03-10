package com.apu.apstay.commands.users;

import com.apu.apstay.enums.Gender;

/**
 *
 * @author alexc
 */
public record UserProfileUpdateCommand (
        String fullName,
        String identityNumber,
        Gender gender,
        String phone,
        String address
) { }
