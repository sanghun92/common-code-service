package com.ejmc.common.exception;

public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String format, Object... args) {
        super(String.format(format, args));
    }
}
