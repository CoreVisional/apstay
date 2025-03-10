package com.apu.apstay.commands.auth;

/**
 *
 * @author alexc
 */
public record RegistrationCreateCommand(
        Long registrantId,
        Long unitId,
        String email,
        String fullName,
        String identityNumber,
        String phone,
        String gender,
        String address
) { }
