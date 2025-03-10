package com.apu.apstay.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author alexc
 */

/**
 * Utility class for AES key management.
 */
public class KeyGeneratorUtil {
    public static SecretKey getKeyFromString(String keyString) throws Exception {
        byte[] keyBytes = keyString.getBytes(StandardCharsets.UTF_8);
        
        // Ensure it's 16 bytes for AES-128
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        keyBytes = sha.digest(keyBytes);
        keyBytes = Arrays.copyOf(keyBytes, 16); // Use only first 16 bytes
        
        return new SecretKeySpec(keyBytes, "AES");
    }
}
