package com.example.demo.model;

import com.example.demo.constant.ParticipationStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

// 사용자 프로젝트 이력 엔티티
@Entity
@Table(name = "user_project_history")
public class UserProjectHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_project_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Enumerated(value = EnumType.STRING)
    private ParticipationStatus status;
}
