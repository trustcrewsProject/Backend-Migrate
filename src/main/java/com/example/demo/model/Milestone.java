package com.example.demo.model;

import java.time.LocalDateTime;
import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 프로젝트 마일스톤 엔티티
@Entity
@Table(name = "milestone")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "milestone_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "milestone_content")
    private String content;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "expire_status")
    private boolean expireStatus;

    @Column(name = "complete_status")
    private boolean completeStatus;

    @Builder
    public Milestone(Long id, Project project, String content, LocalDateTime startDate, LocalDateTime endDate, boolean expireStatus, boolean completeStatus) {
        this.id = id;
        this.project = project;
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.expireStatus = expireStatus;
        this.completeStatus = completeStatus;
    }
}
