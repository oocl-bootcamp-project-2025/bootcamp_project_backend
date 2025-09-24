package com.oocl.bootcampbackend.exception;

public class ExistingAccountException extends BadRequestException {
    public ExistingAccountException(String message) {
        super(message);
    }
}
