package com.oocl.bootcampbackend.exception;

public class NotExistingAccountException extends NotFoundException {
    public NotExistingAccountException(String message) {
        super(message);
    }
}
