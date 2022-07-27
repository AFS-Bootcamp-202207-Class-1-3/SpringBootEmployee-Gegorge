package com.rest.springbootemployee.advice;

public class ErrorResponse {
    private int code;
    private String errorMessage;

    public ErrorResponse(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
