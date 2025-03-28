package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum MilestoneErrorCode implements ErrorCode {
    NOT_FOUND_MILESTONE(HttpStatus.NOT_FOUND, "해당 마일스톤이 존재하지 않습니다."),
    NOT_ALLOWED_CD(HttpStatus.FORBIDDEN, "NOT_ALLOWED_CD"),
    CREATE_EXCEEDED_MS(HttpStatus.BAD_REQUEST, "프로젝트당 최대 마일스톤 갯수는 10개입니다.");

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
