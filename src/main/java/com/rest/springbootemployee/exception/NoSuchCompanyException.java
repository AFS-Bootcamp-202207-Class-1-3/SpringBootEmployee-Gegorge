package com.rest.springbootemployee.exception;

public class NoSuchCompanyException extends RuntimeException{
    @Override
    public String getMessage() {
        return "No Such Company.";
    }
}
