package com.example.demo.dto.milestone.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MileStoneUpdateRequestDto {
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private String progressStatusCode;
}
