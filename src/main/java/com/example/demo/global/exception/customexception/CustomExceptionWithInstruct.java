package com.example.demo.global.exception.customexception;

import com.example.demo.constant.ErrorInstruction;
import com.example.demo.global.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class CustomExceptionWithInstruct extends RuntimeException {
    private ErrorCode errorCode;
    private ErrorInstruction errorInstruction;

    public CustomExceptionWithInstruct(ErrorCode errorCode, ErrorInstruction errorInstruction) {
        this.errorCode = errorCode;
        this.errorInstruction = errorInstruction;
    }
}
