package com.oocl.bootcampbackend.exception;

public class ErrorPasswordException extends BadRequestException {
    public ErrorPasswordException(String message) {
        super(message);
    }

}
