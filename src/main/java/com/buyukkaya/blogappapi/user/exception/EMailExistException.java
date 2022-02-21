package com.buyukkaya.blogappapi.user.exception;

public class EMailExistException extends RuntimeException {

    public EMailExistException(String message) {
        super(message);
    }

    public EMailExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EMailExistException(Throwable cause) {
        super(cause);
    }

    protected EMailExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
