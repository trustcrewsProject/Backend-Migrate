package com.example.demo.dto.common;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class ResponseDto<D> {
    private String message;
    private D data;
}

