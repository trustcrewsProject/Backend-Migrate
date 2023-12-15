package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum BoardErrorCode implements ErrorCode {
    NOT_FOUND_BOARD(HttpStatus.NOT_FOUND, "해당 게시글이 존재하지 않습니다."),
    NO_PERMISSION_TO_EDIT_OR_DELETE(HttpStatus.FORBIDDEN, "해당 게시글을 수정 및 삭제할 권한이 존재하지 않습니다.");

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
