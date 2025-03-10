package com.apu.apstay.exceptions;

/**
 *
 * @author alexc
 */
public class EncryptionException extends BaseException {
    
    public EncryptionException(String message) {
        super(message);
    }

    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
