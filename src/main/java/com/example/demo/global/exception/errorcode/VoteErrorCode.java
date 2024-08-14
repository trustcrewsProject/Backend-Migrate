package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum VoteErrorCode implements ErrorCode{

    VOTE_DUPLICATE(HttpStatus.CONFLICT, "VOTE_DUPLICATE"),
    VOTE_INSUFF_VOTERS(HttpStatus.INTERNAL_SERVER_ERROR,"VOTE_INSUFF_VOTERS"),
    VOTE_EARLY_FW(HttpStatus.INTERNAL_SERVER_ERROR, "VOTE_EARLY_FW"),
    VOTE_EXIST_FW(HttpStatus.INTERNAL_SERVER_ERROR, "VOTE_EXIST_FW"),
    VOTE_NOT_ALLOWED(HttpStatus.FORBIDDEN, "VOTE_NOT_ALLOWED"),
    VOTE_NOT_ALLOWED_YET(HttpStatus.FORBIDDEN, "VOTE_NOT_ALLOWED_YET");

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
