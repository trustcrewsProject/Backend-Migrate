package com.example.demo.dto.milestone.request;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MilestoneUpdateDateRequestDto {
    private LocalDate startDate;
    private LocalDate endDate;
}
