package com.apu.apstay.exceptions;

/**
 *
 * @author alexc
 */
public class BusinessRulesException extends BaseException {
    
    private final String errorKey;

    public BusinessRulesException(String errorKey, String message) {
        super(message);
        this.errorKey = errorKey;
    }

    public BusinessRulesException(String errorKey, String message, Throwable cause) {
        super(message, cause);
        this.errorKey = errorKey;
    }

    public BusinessRulesException(String message) {
        this("global", message);
    }

    public String getErrorKey() {
        return errorKey;
    }
}
