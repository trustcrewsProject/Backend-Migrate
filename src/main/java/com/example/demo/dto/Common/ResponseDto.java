package com.example.demo.dto.Common;

import lombok.*;

@Getter
@AllArgsConstructor
public class ResponseDto<D> {
    private String message;
    private D data;
}
