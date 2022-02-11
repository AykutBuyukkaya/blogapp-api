package com.buyukkaya.blogappapi.security.exception;

public class JwtTokenParsingException extends RuntimeException {

    public JwtTokenParsingException() {
        super();
    }

    public JwtTokenParsingException(String message) {
        super(message);
    }

    public JwtTokenParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtTokenParsingException(Throwable cause) {
        super(cause);
    }

    protected JwtTokenParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
