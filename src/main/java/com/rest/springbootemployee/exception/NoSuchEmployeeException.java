package com.rest.springbootemployee.exception;

public class NoSuchEmployeeException extends RuntimeException{
    @Override
    public String getMessage() {
        return "No such Employee.";
    }
}
