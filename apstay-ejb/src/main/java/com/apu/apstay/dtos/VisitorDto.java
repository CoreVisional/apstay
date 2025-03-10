package com.apu.apstay.dtos;

import com.apu.apstay.enums.Gender;

/**
 *
 * @author alexc
 */
public record VisitorDto(
        Long id,
        String name,
        String identityNumber,
        Gender gender,
        String phone
) { }
