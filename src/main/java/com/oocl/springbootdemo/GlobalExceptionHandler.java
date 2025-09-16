package com.oocl.springbootdemo;


import com.oocl.springbootdemo.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TodoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTodoNotFoundException(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(TodoMissingTextException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String handleTodoMissingTextException(Exception e) {
        return e.getMessage();
    }
}
