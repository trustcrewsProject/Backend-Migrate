package com.example.demo.dto.projectmember.response;

import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProjectMemberWorkWithTrustScoreResponseDto {

    private Long trustScoreHistoryId;
    private String workContent;
    private Integer point;
    private String point_type;
    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime createDate;

    public ProjectMemberWorkWithTrustScoreResponseDto(Long trustScoreHistoryId, String workContent, Integer point, LocalDateTime createDate) {
        this.trustScoreHistoryId = trustScoreHistoryId;
        this.workContent = workContent;
        this.point = point < 0 ? -(point) : point;
        this.point_type = point < 0 ? "minus" : "plus";
        this.createDate = createDate;
    }
}
