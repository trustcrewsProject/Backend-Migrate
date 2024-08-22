package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ProjectPartiErrorCode  implements ErrorCode {
    PARTICIPATE_NOT_ALLOWED(HttpStatus.FORBIDDEN, "PARTICIPATE_NOT_ALLOWED"),
    PARTICIPATE_DUPLICATE(HttpStatus.CONFLICT, "PARTICIPATE_DUPLICATE"),
    PARTICIPATE_EXIST(HttpStatus.CONFLICT, "PARTICIPATE_EXIST" );

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
