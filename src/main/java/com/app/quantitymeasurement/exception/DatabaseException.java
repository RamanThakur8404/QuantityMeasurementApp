package com.app.quantitymeasurement.exception;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String msg, Throwable e) {
        super(msg, e);
    }
}