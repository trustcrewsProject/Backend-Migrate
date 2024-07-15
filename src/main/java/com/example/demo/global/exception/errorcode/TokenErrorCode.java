package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TokenErrorCode implements ErrorCode {
    NON_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "NON_ACCESS_TOKEN"),
    WRONG_TYPE_SIGNATURE(HttpStatus.UNAUTHORIZED, "WRONG_TYPE_SIGNATURE"),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "WRONG_TYPE_TOKEN"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "EXPIRED_TOKEN"),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "MALFORMED_TOKEN"),
    NO_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "NO_REFRESH_TOKEN"),
    INSUFFICIENT_USER_IDENTIFICATION_FOR_TOKEN_REISSUE(
            HttpStatus.UNAUTHORIZED, "INSUFFICIENT_USER_IDENTIFICATION_FOR_TOKEN_REISSUE"),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "INVALID_REFRESH_TOKEN"),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "REFRESH_TOKEN_NOT_FOUND");

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
