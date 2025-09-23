package com.oocl.bootcampbackend.exception;

public class NotExistingUserException extends BadRequestException {
    public NotExistingUserException(String message) {
        super(message);
    }
}
