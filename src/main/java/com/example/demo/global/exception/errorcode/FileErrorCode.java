package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum FileErrorCode implements ErrorCode{
    INVALID_IMAGE_TYPE(HttpStatus.BAD_REQUEST, "올바르지 않은 이미지 타입(JPG, JPEG, PNG) 입니다."),
    FILE_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, "파일 크기가 허용된 한도를 초과했습니다.");

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
