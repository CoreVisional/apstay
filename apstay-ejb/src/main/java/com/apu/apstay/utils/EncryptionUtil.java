package com.apu.apstay.utils;

import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

/**
 *
 * @author alexc
 */
public class EncryptionUtil {
    // The encryption key from configuration (should be kept secret)
    private static final String SECRET_KEY = ConfigUtil.getProperty("ENCRYPTION_KEY");
    
    // AES-GCM parameters
    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12; // 12 bytes (96 bits) is recommended for GCM
    private static final int TAG_LENGTH_BIT = 128; // 128-bit authentication tag

    /**
     * Encrypt sensitive data (e.g., IC Number) using AES/GCM.
     * The output will be IV + cipher text, Base64-encoded.
     * @param data The data to be encrypted.
     * @return The Base64-encoded encrypted data, including the IV.
     */
    public static String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKey key = KeyGeneratorUtil.getKeyFromString(SECRET_KEY);

            // Generate a random 12-byte IV
            byte[] iv = new byte[IV_LENGTH];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);

            // Initialize cipher in ENCRYPT_MODE with GCM parameters
            GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);

            byte[] ciphertext = cipher.doFinal(data.getBytes("UTF-8"));

            // Prepend the IV to the ciphertext (so that we can extract it during decryption)
            byte[] ivAndCiphertext = new byte[IV_LENGTH + ciphertext.length];
            System.arraycopy(iv, 0, ivAndCiphertext, 0, IV_LENGTH);
            System.arraycopy(ciphertext, 0, ivAndCiphertext, IV_LENGTH, ciphertext.length);

            return Base64.getEncoder().encodeToString(ivAndCiphertext);
        } catch (Exception e) {
            // Optionally, log the exception here
            throw new RuntimeException("Error occurred during encryption", e);
        }
    }

    /**
     * Decrypt sensitive data encrypted using AES/GCM.
     * Expects the input to be IV + cipher text (Base64-encoded).
     * @param encryptedData The Base64-encoded encrypted data, including the IV.
     * @return The decrypted data as a String.
     */
    public static String decrypt(String encryptedData) {
        try {
            byte[] ivAndCiphertext = Base64.getDecoder().decode(encryptedData);
            
            // Extract IV and ciphertext
            byte[] iv = new byte[IV_LENGTH];
            byte[] ciphertext = new byte[ivAndCiphertext.length - IV_LENGTH];
            System.arraycopy(ivAndCiphertext, 0, iv, 0, IV_LENGTH);
            System.arraycopy(ivAndCiphertext, IV_LENGTH, ciphertext, 0, ciphertext.length);

            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKey key = KeyGeneratorUtil.getKeyFromString(SECRET_KEY);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(TAG_LENGTH_BIT, iv);
            cipher.init(Cipher.DECRYPT_MODE, key, parameterSpec);

            byte[] decrypted = cipher.doFinal(ciphertext);
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            // Optionally, log the exception here
            throw new RuntimeException("Error occurred during decryption", e);
        }
    }
}
