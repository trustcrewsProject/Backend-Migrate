package com.example.demo.dto.projectAlert.vote.recruit;

import com.example.demo.constant.ProjectAlertType;
import com.example.demo.constant.VoteStatus;
import com.example.demo.dto.common.ConstantDepthDto;
import com.example.demo.dto.common.ConstantDto;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProjectRecruitAlertResponseDto {
    private Long alertId;
    private Long voteId;
    private Long applyId;
    private ConstantDepthDto<ProjectAlertType> alertType;
    private String contents;
    private ConstantDto<VoteStatus> voteStatus;
    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime createDate;

    public ProjectRecruitAlertResponseDto(
            Long project_recruit_alert_id,
            Long project_vote_recruit_id,
            Long applyId,
            ConstantDepthDto<ProjectAlertType> alertType,
            String alertContents,
            ConstantDto<VoteStatus> voteStatus,
            LocalDateTime createDate
    ) {
        this.alertId = project_recruit_alert_id;
        this.voteId = project_vote_recruit_id;
        this.applyId = applyId;
        this.alertType = alertType;
        this.contents = alertContents;
        this.createDate = createDate;
        this.voteStatus = voteStatus;
    }
}
