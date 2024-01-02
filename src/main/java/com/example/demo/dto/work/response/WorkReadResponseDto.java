package com.example.demo.dto.work.response;

import com.example.demo.constant.ProgressStatus;
import com.example.demo.model.work.Work;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class WorkReadResponseDto {
    private Long workId;
    private Long projectId;
    private Long milestoneId;
    private String assignedUserNickname;
    private String lastModifiedMemberNickname;
    private String content;
    private LocalDate startDate;
    private LocalDate endDate;
    private String progressStatus;

    public WorkReadResponseDto(Long workId, Long projectId, Long milestoneId, String assignedUserNickname, String lastModifiedMemberNickname,
                               String content, LocalDate startDate, LocalDate endDate, ProgressStatus progressStatus) {
        this.workId = workId;
        this.projectId = projectId;
        this.milestoneId = milestoneId;
        this.assignedUserNickname = assignedUserNickname;
        this.lastModifiedMemberNickname = lastModifiedMemberNickname;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.progressStatus = progressStatus.getDescription();
    }

    public static WorkReadResponseDto of(Work work) {
        return WorkReadResponseDto.builder()
                .workId(work.getId())
                .projectId(work.getProject().getId())
                .milestoneId(work.getMilestone().getId())
                .assignedUserNickname(work.getAssignedUserId().getNickname())
                .lastModifiedMemberNickname(work.getLastModifiedMember().getUser().getNickname())
                .content(work.getContent())
                .startDate(work.getStartDate())
                .endDate(work.getEndDate())
                .progressStatus(work.getProgressStatus().getDescription())
                .build();
    }
}
