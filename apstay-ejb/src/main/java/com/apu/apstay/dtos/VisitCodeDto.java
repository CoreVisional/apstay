package com.apu.apstay.dtos;

/**
 *
 * @author alexc
 */
public record VisitCodeDto(
        Long id,
        Long visitRequestId,
        String code,
        boolean active
) {}
