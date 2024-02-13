package com.example.demo.dto.projectmember.response;

import com.example.demo.constant.ProgressStatus;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
public class ProjectMemberWorkWithTrustScoreResponseDto {

    private Long workId;
    private String workContent;
    private String workContentDetail;
    private LocalDate startDate;
    private LocalDate endDate;
    private String progressStatus;
    private Long trustScoreHistoryId;
    private Integer point;
    private String point_type;

    public ProjectMemberWorkWithTrustScoreResponseDto(Long workId, String workContent, String workContentDetail, LocalDate startDate,
                                                      LocalDate endDate, ProgressStatus progressStatus, Long trustScoreHistoryId, Integer point) {
        this.workId = workId;
        this.workContent = workContent;
        this.workContentDetail = workContentDetail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.progressStatus = progressStatus.getDescription();
        this.trustScoreHistoryId = trustScoreHistoryId;
        this.point = point < 0 ? -(point) : point;
        this.point_type = point < 0 ? "minus" : "plus";
    }
}
