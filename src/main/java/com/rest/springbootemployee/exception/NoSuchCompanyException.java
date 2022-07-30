package com.rest.springbootemployee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchCompanyException extends RuntimeException {
    @Override
    public String getMessage() {
        return "No Such Company.";
    }
}
