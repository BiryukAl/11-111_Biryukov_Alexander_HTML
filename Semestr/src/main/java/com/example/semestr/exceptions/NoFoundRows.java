package com.example.semestr.exceptions;

public class NoFoundRows extends DbException{
    public NoFoundRows() {
        super();
    }

    public NoFoundRows(String message) {
        super(message);
    }

    public NoFoundRows(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFoundRows(Throwable cause) {
        super(cause);
    }

    protected NoFoundRows(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
