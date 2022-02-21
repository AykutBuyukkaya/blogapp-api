package com.buyukkaya.blogappapi.blogpost.exception;

public class BlogDoesNotFoundException extends RuntimeException {

    public BlogDoesNotFoundException(String message) {
        super(message);
    }

    public BlogDoesNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public BlogDoesNotFoundException(Throwable cause) {
        super(cause);
    }

    protected BlogDoesNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
