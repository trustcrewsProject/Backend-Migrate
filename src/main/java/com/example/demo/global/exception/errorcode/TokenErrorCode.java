package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TokenErrorCode implements ErrorCode {
    NON_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 인증정보로 인해 사용자 인증에 실패했습니다."),
    WRONG_TYPE_SIGNATURE(HttpStatus.UNAUTHORIZED, "유효하지 않은 인증정보로 인해 사용자 인증에 실패했습니다."),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 인증정보로 인해 사용자 인증에 실패했습니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "서비스 이용을 위해 로그인해주세요."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 인증정보로 인해 사용자 인증에 실패했습니다."),
    NO_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "서비스 이용을 위해 로그인해주세요."),
    INSUFFICIENT_USER_IDENTIFICATION_FOR_TOKEN_REISSUE(
            HttpStatus.UNAUTHORIZED, "유효하지 않은 인증정보로 인해 사용자 인증에 실패했습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 인증정보로 인해 사용자 인증에 실패했습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "서비스 이용을 위해 로그인해주세요.");

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
