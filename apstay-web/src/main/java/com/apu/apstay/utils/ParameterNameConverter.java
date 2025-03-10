package com.apu.apstay.utils;

/**
 *
 * @author alexc
 */
public class ParameterNameConverter {
    /**
     * Converts a dashed string (e.g., "login-key") to camelCase (e.g., "loginKey").
     *
     * @param input the dashed string
     * @return the camelCase version of the string
     */
    public static String dashToCamelCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        var result = new StringBuilder();
        boolean capitalizeNext = false;
        for (char c : input.toCharArray()) {
            if (c == '-') {
                capitalizeNext = true;
            } else {
                result.append(capitalizeNext ? Character.toUpperCase(c) : c);
                capitalizeNext = false;
            }
        }
        return result.toString();
    }
}
