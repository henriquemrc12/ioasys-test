package com.ioasys.api.shared.exception;

public class ExceptionResponse {

    private String message;
    private Integer status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ExceptionResponse() {
    }

    public ExceptionResponse(String message, Integer status) {
        this.message = message;
        this.status = status;
    }
}