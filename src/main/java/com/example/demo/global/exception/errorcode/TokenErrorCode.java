package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TokenErrorCode implements ErrorCode {
    NON_ACCESS_TOKEN(HttpStatus.BAD_REQUEST, "NON_ACCESS_TOKEN"),
    WRONG_TYPE_SIGNATURE(HttpStatus.BAD_REQUEST, "WRONG_TYPE_SIGNATURE"),
    WRONG_TYPE_TOKEN(HttpStatus.BAD_REQUEST, "WRONG_TYPE_TOKEN"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "EXPIRED_TOKEN"),
    MALFORMED_TOKEN(HttpStatus.BAD_REQUEST, "MALFORMED_TOKEN"),
    NO_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "NO_REFRESH_TOKEN"),
    INSUFFICIENT_USER_IDENTIFICATION_FOR_TOKEN_REISSUE(
            HttpStatus.BAD_REQUEST, "INSUFFICIENT_USER_IDENTIFICATION_FOR_TOKEN_REISSUE"),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "INVALID_REFRESH_TOKEN"),
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
