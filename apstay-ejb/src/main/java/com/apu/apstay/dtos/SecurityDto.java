package com.apu.apstay.dtos;

import com.apu.apstay.enums.CommuteMode;

/**
 *
 * @author alexc
 */
public record SecurityDto (
        CommuteMode commuteMode
) {}
