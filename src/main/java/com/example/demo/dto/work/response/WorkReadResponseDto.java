package com.example.demo.dto.work.response;

import com.example.demo.constant.ProgressStatus;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
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
    private WorkAssignedUserInfoResponseDto assignedUser;
    private String lastModifiedMemberNickname;
    private String content;
    private String contentDetail;
    private LocalDate startDate;
    private LocalDate endDate;
    private String progressStatus;

    public WorkReadResponseDto(Long workId, Long projectId, Long milestoneId, WorkAssignedUserInfoResponseDto assignedUser, String lastModifiedMemberNickname,
                               String content, String contentDetail, LocalDate startDate, LocalDate endDate, ProgressStatus progressStatus) {
        this.workId = workId;
        this.projectId = projectId;
        this.milestoneId = milestoneId;
        this.assignedUser = assignedUser;
        this.lastModifiedMemberNickname = lastModifiedMemberNickname;
        this.content = content;
        this.contentDetail = contentDetail;
        this.startDate = startDate;
        this.endDate = endDate;
        this.progressStatus = progressStatus.getDescription();
    }

    public static WorkReadResponseDto of(Work work, ProjectMember projectMember, User assignedUser) {
        return WorkReadResponseDto.builder()
                .workId(work.getId())
                .projectId(work.getProject().getId())
                .milestoneId(work.getMilestone().getId())
                .assignedUser(WorkAssignedUserInfoResponseDto.of(projectMember.getId(), assignedUser.getNickname()))
                .lastModifiedMemberNickname(work.getLastModifiedMember().getUser().getNickname())
                .content(work.getContent())
                .contentDetail(work.getContentDetail())
                .startDate(work.getStartDate())
                .endDate(work.getEndDate())
                .progressStatus(work.getProgressStatus().getDescription())
                .build();
    }
}
