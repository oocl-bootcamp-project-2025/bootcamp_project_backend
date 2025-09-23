package com.oocl.bootcampbackend.exception;

public class ExistingUserException extends BadRequestException {
    public ExistingUserException(String message) {
        super(message);
    }
}
