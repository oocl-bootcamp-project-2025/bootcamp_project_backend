package com.oocl.springbootdemo;


import com.oocl.springbootdemo.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEmployeeNotFoundException(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCompanyNotFoundException(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(TodoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTodoNotFoundException(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(EmployeeNotActiveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmployeeNotActiveException(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(EmployeeNotAmongLegalAgeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmployeeNotAmongLegalAgeException(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(EmployeeNotHavingAcceptablePaidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmployeeNotHavingAcceptablePaidException(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(EmployeeNameAndGenderDuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleEmployeeNameAndGenderDuplicatedException(Exception e) {
        return e.getMessage();
    }
}
