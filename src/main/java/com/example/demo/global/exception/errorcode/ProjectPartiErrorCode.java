package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ProjectPartiErrorCode  implements ErrorCode {
    PARTICIPATE_NOT_ALLOWED(HttpStatus.FORBIDDEN, "강제 탈퇴 멤버는 프로젝트에 참가할 수 없습니다."),

    PARTICIPATE_DUPLICATE(HttpStatus.CONFLICT, "이미 참여중인 프로젝트 입니다.");

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
