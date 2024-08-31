package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum WorkErrorCode implements ErrorCode {
    NOT_FOUND_WORK(HttpStatus.NOT_FOUND, "NOT_FOUND_WORK"),
    NO_PERMISSION_TO_TASK(HttpStatus.FORBIDDEN, "NO_PERMISSION_TO_TASK"),
    CREATE_EXCEEDED_WORK(HttpStatus.BAD_REQUEST, "CREATE_EXCEEDED_WORK");

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
