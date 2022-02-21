package com.buyukkaya.blogappapi.user.exception;

public class UsernameExistException extends RuntimeException {

    public UsernameExistException(String message) {
        super(message);
    }

    public UsernameExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameExistException(Throwable cause) {
        super(cause);
    }

    protected UsernameExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
