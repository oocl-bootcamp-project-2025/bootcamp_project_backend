package com.oocl.bootcampbackend.exception;

public class NullPhoneException extends BadRequestException{
    public NullPhoneException(String message) {
        super(message);
    }
}
