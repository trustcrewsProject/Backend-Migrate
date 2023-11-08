package com.example.demo.dto.work;

import com.example.demo.model.ProjectMilestone;
import com.example.demo.model.ProjectWork;
import com.example.demo.model.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

// 업무 정보 응답 DTO
@Getter
@Builder
public class WorkInfoResponseDto {

    private String workContent;

    private User createUser;

    private LocalDateTime createDate;

    // 업무기간
    private LocalDate startDate;
    private LocalDate endDate;

    // 업무 참여 멤버

    private boolean completionStatus;

    private ProjectMilestone projectMilestone;

    public static WorkInfoResponseDto of (ProjectWork work, ProjectMilestone milestone) {
        return WorkInfoResponseDto.builder()
                .workContent(work.getContent())
                .createUser(work.getAssignedUserId())
                .createDate(work.getCreateDate())
                .completionStatus(work.isCompletionStatus())
                .projectMilestone(milestone)
                .build();
    }
}
