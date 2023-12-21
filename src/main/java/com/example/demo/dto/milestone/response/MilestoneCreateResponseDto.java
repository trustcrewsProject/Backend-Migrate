package com.example.demo.dto.milestone;

import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.example.demo.model.milestone.Milestone;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MilestoneCreateResponseDto {
    private Long mileStoneId;
    private Long projectId;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private String progressStatus;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime createDate;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime updateDate;

    public static MilestoneCreateResponseDto of(Milestone milestone) {
        return MilestoneCreateResponseDto.builder()
                .mileStoneId(milestone.getId())
                .projectId(milestone.getProject().getId())
                .content(milestone.getContent())
                .startDate(milestone.getStartDate())
                .endDate(milestone.getEndDate())
                .progressStatus(milestone.getProgressStatus().getDescription())
                .createDate(milestone.getCreateDate())
                .updateDate(milestone.getUpdateDate())
                .build();
    }
}
