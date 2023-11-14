package com.example.demo.model;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Getter;

// 프로젝트 업무 엔티티
@Entity
@Table(name = "work")
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User assignedUserId;

    @Column(name = "work_content")
    private String content;

    @Column(name = "expire_statue")
    private boolean expireStatus;

    @Column(name = "completion_status")
    private boolean completionStatus;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @OneToOne
    @JoinColumn(name = "project_member_id")
    private ProjectMember lastModifiedMember;
}
