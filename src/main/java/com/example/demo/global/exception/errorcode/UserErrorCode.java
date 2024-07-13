package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "NOT_FOUND_USER"),
    IN_USE_EMAIL(HttpStatus.CONFLICT, "IN_USE_EMAIL"),
    IN_USE_NICKNAME(HttpStatus.CONFLICT, "IN_USE_NICKNAME"),
    NO_PROFILE_IMG(HttpStatus.NOT_FOUND, "NO_PROFILE_IMG"),
    AUTHENTICATION_FAIL(HttpStatus.UNAUTHORIZED, "AUTHENTICATION_FAIL"),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "ACCESS_DENIED"),
    IN_USE_OAUTH_USER(HttpStatus.CONFLICT, "IN_USE_OAUTH_USER");

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
