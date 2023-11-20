package com.example.demo.dto.common;

import lombok.*;

@Getter
@AllArgsConstructor
public class ResponseDto<D> {
    private String message;
    private D data;
}
