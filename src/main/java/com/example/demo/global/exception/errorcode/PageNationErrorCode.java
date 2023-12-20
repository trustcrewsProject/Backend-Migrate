package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum PageNationErrorCode implements ErrorCode{
    INVALID_PAGE_NUMBER(HttpStatus.BAD_REQUEST, "페이지 번호는 최소 0번 이상 이여야 합니다.");

    private HttpStatus status;
    private String message;

    @Override
    public HttpStatus getStatus() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
