package com.example.demo.model;

import com.example.demo.constant.ProjectStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

// 프로젝트 엔티티
@Entity
@Getter
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trust_grade_id")
    private TrustGrade trustGrade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    @Enumerated(value = EnumType.STRING)
    private ProjectStatus status;

    private int fe;

    private int be;

    private int planner;

    private int designer;

    private int publisher;

    private LocalDateTime startDate;

    private LocalDateTime endDate;
}
