package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ProjectErrorCode implements ErrorCode {
    NOT_FOUND_PROJECT(HttpStatus.NOT_FOUND, "프로젝트가 존재하지 않습니다."),
    ACCESS_NOT_ALLOWED(HttpStatus.FORBIDDEN, "요청한 정보에 대한 접근 권한이 없습니다."),
    NO_PERMISSION_TO_TASK(HttpStatus.FORBIDDEN, "요청한 작업에 대한 수행 권한이 없습니다.");

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
