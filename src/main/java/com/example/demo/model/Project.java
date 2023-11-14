package com.example.demo.model;

import com.example.demo.constant.ProjectStatus;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;

// 프로젝트 엔티티
@Entity
@Table(name = "project")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Project extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_id")
    private Long id;

    private String name;
    private String subject;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trust_grade_id")
    private TrustGrade trustGrade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "project_status")
    @Enumerated(value = EnumType.STRING)
    private ProjectStatus status;

    @Column(nullable = true)
    private int crewNumber;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    public Project(Long id, String name, String subject, TrustGrade trustGrade, User user, ProjectStatus status, int crewNumber, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.name = name;
        this.subject = subject;
        this.trustGrade = trustGrade;
        this.user = user;
        this.status = status;
        this.crewNumber = crewNumber;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
