package com.example.demo.dto.work.request;

import com.example.demo.constant.ProgressStatus;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class WorkCreateRequestDto {
    @NotNull(message = "프로젝트 아이디는 필수 요청 값입니다.")
    private Long projectId;
    @NotNull(message = "마일스톤 아이디는 필수 요청 값입니다.")
    private Long milestoneId;
    @NotBlank(message = "업무 내용은 필수 요청 값입니다.")
    private String content;

    @NotBlank(message = "업무 상세 내용은 필수 요청 값입니다.")
    private String contentDetail;

    @NotNull(message = "업무 시작 날짜는 필수 요청 값입니다.")
    private LocalDate startDate;

    @NotNull(message = "업무 종료 날짜는 필수 요청 값입니다.")
    private LocalDate endDate;

    @NotNull(message = "업무 할당자는 필수 요청 값입니다.")
    private Long assignedUserId;

    public Work toWorkEntity(
            Project project, Milestone milestone, User user, ProjectMember projectMember) {

        ProgressStatus progressStatus = ProgressStatus.ON_GOING;

        LocalDate today = LocalDate.now();
        if(this.getStartDate().isAfter(today)){
            progressStatus = ProgressStatus.BEFORE_START;
        }

        return Work.builder()
                .project(project)
                .milestone(milestone)
                .assignedUserId(user)
                .lastModifiedMember(projectMember)
                .content(this.getContent())
                .contentDetail(this.getContentDetail())
                .progressStatus(progressStatus)
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .build();
    }
}
