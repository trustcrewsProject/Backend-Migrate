package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum OAuth2ErrorCode implements ErrorCode{

    NOT_FOUND_OAUTH_PRINCIPAL(HttpStatus.NOT_FOUND, "OAuth 인증 정보를 찾을 수 없습니다.");

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
