package com.example.demo.dto.projectmember.response;

import com.example.demo.constant.ProgressStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class ProjectMemberWorkWithTrustScoreResponseDto {

    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private String progressStatus;
    private Integer point;
    private String point_type;

    public ProjectMemberWorkWithTrustScoreResponseDto(String content, LocalDate startDate,
                                                      LocalDate endDate, ProgressStatus progressStatus, Integer point, String point_type) {
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.progressStatus = progressStatus.getDescription();
        this.point = point;
        this.point_type = Objects.nonNull(point_type) ? (point_type.equals("M") ? "minus" : "plus") : null;
    }
}
