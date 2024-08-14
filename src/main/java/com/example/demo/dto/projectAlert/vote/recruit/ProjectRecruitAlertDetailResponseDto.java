package com.example.demo.dto.projectAlert.vote.recruit;

import com.example.demo.constant.ProjectAlertType;
import com.example.demo.constant.VoteStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectRecruitAlertDetailResponseDto {
    private Long alertId;
    private Long voteId;
    private Long project_apply_id;
    private ProjectAlertType alertType;
    private String alertContents;
    private VoteStatus voteStatus;
    private int agrees;
    private int disagrees;
    private int maxVoteCount;

    public ProjectRecruitAlertDetailResponseDto(
            Long alertId,
            Long voteId,
            Long project_apply_id,
            ProjectAlertType alertType,
            String alertContents,
            VoteStatus voteStatus,
            int agrees,
            int disagrees,
            int maxVoteCount
    ) {
        this.alertId =  alertId;
        this.voteId =  voteId;
        this.project_apply_id = project_apply_id;
        this.alertType = alertType;
        this.alertContents = alertContents;
        this.voteStatus = voteStatus;
        this.agrees = agrees;
        this.disagrees = disagrees;
        this.maxVoteCount = maxVoteCount;
    }
}
