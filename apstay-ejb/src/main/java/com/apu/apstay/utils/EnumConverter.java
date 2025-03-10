package com.apu.apstay.utils;

/**
 *
 * @author alexc
 */
public class EnumConverter {
    
    public static <T extends Enum<T>> T stringToEnum(String value, Class<T> enumClass) {
        if (value == null) return null;
        try {
            return Enum.valueOf(enumClass, value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
