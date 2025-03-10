package com.apu.apstay.commands.units;

/**
 *
 * @author alexc
 */
public record UnitCreateCommand(
        String unitName,
        int floorNumber
) { }
