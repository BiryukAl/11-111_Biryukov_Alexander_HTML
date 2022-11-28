package com.example.semestr.exceptions;

public class NotUniqueLogin extends DbException{
    public NotUniqueLogin() {
        super();
    }

    public NotUniqueLogin(String message) {
        super(message);
    }

    public NotUniqueLogin(String message, Throwable cause) {
        super(message, cause);
    }

    public NotUniqueLogin(Throwable cause) {
        super(cause);
    }

    protected NotUniqueLogin(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
