package com.apu.apstay.dtos;

/**
 *
 * @author alexc
 */
public record ResidentDto(
        Long id,
        String fullName,
        String email,
        String unitName,
        int floorNumber
) {}
