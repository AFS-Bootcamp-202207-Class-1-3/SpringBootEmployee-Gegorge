package com.rest.springbootemployee.advice;

import com.rest.springbootemployee.exception.NoSuchCompanyException;
import com.rest.springbootemployee.exception.NoSuchEmployeeException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoSuchCompanyException.class, NoSuchEmployeeException.class})
    public ErrorResponse handleNotFoundException(Exception e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }
}
