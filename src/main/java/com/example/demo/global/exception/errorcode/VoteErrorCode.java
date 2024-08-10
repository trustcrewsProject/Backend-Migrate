package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum VoteErrorCode implements ErrorCode{

    VOTE_DUPLICATE(HttpStatus.CONFLICT, "VOTE_DUPLICATE"),
    VOTE_NOT_ALLOWED(HttpStatus.FORBIDDEN, "VOTE_NOT_ALLOWED");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
