package com.devcourse.web2_1_dashbunny_be.exception;

public class DifferentStoreException extends RuntimeException {
    public DifferentStoreException(String message) {
        super(message);
    }
}