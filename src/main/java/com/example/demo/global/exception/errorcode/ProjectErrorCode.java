package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ProjectErrorCode implements ErrorCode {
    NOT_FOUND_PROJECT(HttpStatus.NOT_FOUND, "NOT_FOUND_PROJECT"),
    ACCESS_NOT_ALLOWED(HttpStatus.FORBIDDEN, "ACCESS_NOT_ALLOWED"),
    NO_PERMISSION_TO_TASK(HttpStatus.FORBIDDEN, "NO_PERMISSION_TO_TASK");

    private HttpStatus status;
    private String message;

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
