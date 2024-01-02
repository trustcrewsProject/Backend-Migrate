package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum PageNationErrorCode implements ErrorCode {
    INVALID_PAGE_NUMBER(HttpStatus.BAD_REQUEST, "페이지 번호는 최소 0번 이상 이여야 합니다."),
    INVALID_PAGE_ITEM_COUNT(HttpStatus.BAD_REQUEST, "한 페이지에 요청할 수 있는 데이터의 개수 범위를 확인해주세요.");

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
