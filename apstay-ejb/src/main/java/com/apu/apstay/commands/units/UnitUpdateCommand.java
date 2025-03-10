package com.apu.apstay.commands.units;

/**
 *
 * @author alexc
 */
public record UnitUpdateCommand(
        String unitName,
        int floorNumber,
        boolean occupied
) { }
