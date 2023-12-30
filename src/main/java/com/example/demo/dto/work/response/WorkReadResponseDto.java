package com.example.demo.dto.work.response;

import com.example.demo.model.work.Work;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
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
