package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum VoteErrorCode implements ErrorCode{

    VOTE_DUPLICATE(HttpStatus.CONFLICT, "이미 투표를 완료했습니다."),
    VOTE_INSUFF_VOTERS(HttpStatus.INTERNAL_SERVER_ERROR,"강제탈퇴 투표는 탈퇴 대상자를 제외한 최소 2명의 투표자가 필요합니다."),
    VOTE_EARLY_FW(HttpStatus.INTERNAL_SERVER_ERROR, "VOTE_EARLY_FW"),
    VOTE_EXIST_FW(HttpStatus.INTERNAL_SERVER_ERROR, "VOTE_EXIST_FW"),
    VOTE_NOT_ALLOWED(HttpStatus.FORBIDDEN, "강제탈퇴 투표는 동시에 1개 이상 진행할 수 없습니다."),
    VOTE_NOT_ALLOWED_YET(HttpStatus.FORBIDDEN, "프로젝트 참여 3일 후 부터 투표에 참가할 수 있습니다."),
    VOTE_TARGET_NOT_ALLOWED(HttpStatus.FORBIDDEN, "투표 대상자는 투표에 참여할 수 없습니다.");

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
