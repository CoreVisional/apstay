package com.apu.apstay.exceptions;

/**
 *
 * @author alexc
 */
public class EmailException extends BaseException {
    public EmailException(String message) {
        super(message);
    }
    
    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
