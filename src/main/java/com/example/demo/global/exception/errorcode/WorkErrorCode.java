package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum WorkErrorCode implements ErrorCode {
    NOT_FOUND_WORK(HttpStatus.NOT_FOUND, "NOT_FOUND_WORK"),
    NO_PERMISSION_TO_TASK(HttpStatus.FORBIDDEN, "해당 작업에 대한 권한이 없습니다."),
    CREATE_EXCEEDED_WORK(HttpStatus.BAD_REQUEST, "마일스톤당 최대 업무 갯수는 100개입니다.");

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
