package com.example.demo.dto.work.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkUpdateRequestDto {
    private String content;
    private String contentDetail;
    private LocalDate startDate;
    private LocalDate endDate;
    private String progressStatusCode;
    private Long assignedUserId;
}
