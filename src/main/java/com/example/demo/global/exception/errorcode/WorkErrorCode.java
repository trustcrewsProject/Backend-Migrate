package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum WorkErrorCode implements ErrorCode {
    NOT_FOUND_WORK(HttpStatus.NOT_FOUND, "해당 작업이 존재하지 않습니다."),
    NO_PERMISSION_TO_TASK(HttpStatus.FORBIDDEN, "해당 작업을 처리할 권한이 존재하지 않습니다.");

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
