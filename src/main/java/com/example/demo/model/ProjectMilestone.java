package com.example.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;

// 프로젝트 마일스톤 엔티티
@Entity
@Table(name = "project_milestone")
public class ProjectMilestone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_milestone_id")
    private Long id;

    @Column(name = "milestone_content")
    private String content;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "expires_status")
    private boolean expiresStatus;

    @Column(name = "completion_status")
    private boolean completionStatus;
}
