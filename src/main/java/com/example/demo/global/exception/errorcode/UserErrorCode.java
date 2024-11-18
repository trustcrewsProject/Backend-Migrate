package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 사용자 입니다."),
    IN_USE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일주소 입니다."),
    IN_USE_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임 입니다."),
    NO_PROFILE_IMG(HttpStatus.NOT_FOUND, "프로필 이미지가 존재하지 않습니다."),
    AUTHENTICATION_FAIL(HttpStatus.UNAUTHORIZED, "사용자 인증에 실패하였습니다. 이메일 혹은 비밀번호를 확인해주세요."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "요청한 정보에 대한 접근 권한이 없습니다.");

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
