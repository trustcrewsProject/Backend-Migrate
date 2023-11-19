package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ProjectErrorCode implements ErrorCode {
    NOT_FOUND_PROJECT(HttpStatus.NOT_FOUND, "B_001", "해당 프로젝트가 존재하지 않습니다.");

    private HttpStatus status;
    private String code;
    private String message;

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}