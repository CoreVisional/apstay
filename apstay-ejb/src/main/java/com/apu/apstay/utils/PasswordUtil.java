package com.apu.apstay.utils;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

/**
 *
 * @author alexc
 */
public class PasswordUtil {
    private static final String PEPPER = ConfigUtil.getProperty("PASSWORD_PEPPER");

    public static String hashPassword(String password) {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 1, 60000, 10); // Argon2 configuration
        return encoder.encode(password + PEPPER); // Prepend pepper before hashing
    }

    public static boolean verifyPassword(String hashedPassword, String password) {
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(16, 32, 1, 60000, 10); // Same Argon2 config
        return encoder.matches(password + PEPPER, hashedPassword); // Prepend pepper for verification
    }
}