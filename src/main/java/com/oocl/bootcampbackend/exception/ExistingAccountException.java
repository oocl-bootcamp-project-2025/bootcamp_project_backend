package com.oocl.bootcampbackend.exception;

public class ExistingAccountException extends ConfictPhoneException {
    public ExistingAccountException(String message) {
        super(message);
    }
}
