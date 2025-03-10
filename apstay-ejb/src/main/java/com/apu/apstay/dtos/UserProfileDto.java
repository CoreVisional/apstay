package com.apu.apstay.dtos;

import com.apu.apstay.enums.Gender;

/**
 *
 * @author alexc
 */
public record UserProfileDto(
        Long id,
        Long userId,
        String name,
        String identityNumber,
        Gender gender,
        String phone,
        String address
) { }
