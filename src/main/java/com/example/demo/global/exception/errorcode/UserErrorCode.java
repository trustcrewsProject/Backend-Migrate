package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum UserErrorCode implements ErrorCode {
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 유저가 존재하지 않습니다."),
    ALREADY_EMAIL(HttpStatus.CONFLICT, "이미 사용중인 이메일입니다."),
    ALREADY_NICKNAME(HttpStatus.CONFLICT, "이미 사용중인 닉네임입니다."),
    DOES_NOT_EXIST_PROFILE_IMG(HttpStatus.NOT_FOUND, "요청한 회원의 프로필 이미지가 존재하지 않습니다."),
    INVALID_AUTHENTICATION(HttpStatus.BAD_REQUEST, "사용자 인증에 실패하였습니다. 이메일 혹은 비밀번호를 확인해주세요."),
    ACCESS_DENIED(HttpStatus.UNAUTHORIZED, "요청한 URI에 접근할 권한이 존재하지 않습니다.");

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
