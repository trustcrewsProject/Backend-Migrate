package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ProjectMemberErrorCode implements ErrorCode {
    NOT_FOUND_PROJECT_MEMBER(HttpStatus.NOT_FOUND, "해당 프로젝트 멤버가 존재하지 않습니다."),
    NO_OTHER_PROJECT_MANAGER(HttpStatus.INTERNAL_SERVER_ERROR, "프로젝트에는 최소 1명의 매니저가 필요합니다. \"프로젝트 설정\"에서 다른 크루 최소 1명에게 매니저 권한을 부여하고 탈퇴를 시도해 주세요.");

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
