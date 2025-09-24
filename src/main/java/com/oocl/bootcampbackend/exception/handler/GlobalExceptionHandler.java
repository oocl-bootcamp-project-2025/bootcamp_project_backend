package com.oocl.bootcampbackend.exception.handler;


import com.oocl.bootcampbackend.exception.BadRequestException;
import com.oocl.bootcampbackend.exception.ConfictPhoneException;
import com.oocl.bootcampbackend.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestException(BadRequestException e) {
        return e.getMessage();
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(ConfictPhoneException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleConfictPhoneException(ConfictPhoneException e) {
        return e.getMessage();
    }
}
