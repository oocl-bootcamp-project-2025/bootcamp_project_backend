package com.oocl.bootcampbackend.exception;

public class NotExistingUserException extends NotFoundException {
    public NotExistingUserException(String message) {
        super(message);
    }
}
