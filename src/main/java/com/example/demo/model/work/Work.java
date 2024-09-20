package com.example.demo.model.work;

import com.example.demo.constant.ProgressStatus;
import com.example.demo.dto.work.request.WorkUpdateContentRequestDto;
import com.example.demo.dto.work.request.WorkUpdateRequestDto;
import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 프로젝트 업무 엔티티
@Entity
@Table(name = "work")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Work extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "work_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User assignedUserId;

    @Column(name = "work_content")
    private String content;

    @Column(name = "content_detail", columnDefinition = "TEXT")
    private String contentDetail;

    @Enumerated(EnumType.STRING)
    @Column(name = "progressStatus")
    private ProgressStatus progressStatus;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_member_id")
    private ProjectMember lastModifiedMember;

    @Column(name = "complete_date")
    private LocalDateTime completeDate;

    @Builder
    public Work(
            Project project,
            Milestone milestone,
            User assignedUserId,
            String content,
            String contentDetail,
            ProgressStatus progressStatus,
            LocalDate startDate,
            LocalDate endDate,
            ProjectMember lastModifiedMember) {
        this.project = project;
        this.milestone = milestone;
        this.assignedUserId = assignedUserId;
        this.content = content;
        this.contentDetail = contentDetail;
        this.progressStatus = progressStatus;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lastModifiedMember = lastModifiedMember;
        this.completeDate = null;
    }

    public void update(WorkUpdateRequestDto dto, ProjectMember projectMember, User assignedUser) {
        this.content = dto.getContent();
        this.contentDetail = dto.getContentDetail();

        String currentProgressStatusCode = this.progressStatus.getCode();
        LocalDate changeStartDate = dto.getStartDate();
        LocalDate today = LocalDate.now();

        if ( // ‘시작전’인데 && 날짜 바꾸는 경우 && 바꾸는 날짜가 오늘 이전이거나 같은 경우(:진행중)
                currentProgressStatusCode.equals(ProgressStatus.PS001.getCode())
                        && !this.getStartDate().isEqual(changeStartDate)
                        && (changeStartDate.isBefore(today) || changeStartDate.isEqual(today))
        ) {
            this.progressStatus = ProgressStatus.PS002;
        } else if ( // ‘진행중’ 인데 && 상태 안 바꾸는 경우 && 바꾸는 날짜가 오늘 이후인 경우 (: 시작전)
                currentProgressStatusCode.equals(ProgressStatus.PS002.getCode())
                        && this.progressStatus.getCode().equals(dto.getProgressStatus().getCode())
                        && changeStartDate.isAfter(today)
        ) {
            this.progressStatus = ProgressStatus.PS001;
        }else{
            this.progressStatus = ProgressStatus.getProgressStatusByCode(dto.getProgressStatus().getCode());
        }
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
        this.lastModifiedMember = projectMember;
        this.assignedUserId = assignedUser;
    }

    public void updateContent(WorkUpdateContentRequestDto dto, ProjectMember projectMember) {
        this.content = dto.getContent();
        this.lastModifiedMember = projectMember;
    }

    public void updateCompleteStatus(ProgressStatus progressStatus) {
        this.progressStatus = progressStatus;
    }

    public void updateAssignedUserId(User user, ProjectMember projectMember) {
        this.assignedUserId = user;
        this.lastModifiedMember = projectMember;
    }

    public void updateCompleteDate(LocalDateTime completeDate, ProjectMember projectMember) {
        this.completeDate = completeDate;
        this.lastModifiedMember = projectMember;
    }
}
