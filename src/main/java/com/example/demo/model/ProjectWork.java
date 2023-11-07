package com.example.demo.model;


import javax.persistence.*;
import java.time.LocalDateTime;

// 프로젝트 업무 엔티티
@Entity
@Table(name = "project_work")
public class ProjectWork extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_work_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_milestone_id")
    private ProjectMilestone projectMilestone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User createUser;

    @Column(name = "work_content")
    private String content;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "expires_statue")
    private boolean expiresStatus;

    @Column(name = "completion_status")
    private boolean completionStatus;

    @OneToOne
    @JoinColumn(name = "project_member_id")
    private ProjectMember lastModifiedMember;
}
