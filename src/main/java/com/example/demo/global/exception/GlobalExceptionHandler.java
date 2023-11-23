package com.example.demo.global.exception;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.global.exception.customexception.CustomException;
import com.example.demo.global.exception.errorcode.CommonErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid 어노테이션 유효성 검사 실패 시 발생 Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<?>> processValidationError(MethodArgumentNotValidException e) {
        List<ResponseDto.CustomFieldError> errors = e.getFieldErrors().stream()
                .filter(fieldError -> fieldError != null)
                .map(ResponseDto.CustomFieldError::new).collect(Collectors.toList());

        final ResponseDto<List<ResponseDto.CustomFieldError>> response =
                ResponseDto.fail("", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // CustomException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto<?>> customExceptionHandle(CustomException e) {
        final ResponseDto response = ResponseDto.fail(e.getErrorCode().getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(response);
    }
}
