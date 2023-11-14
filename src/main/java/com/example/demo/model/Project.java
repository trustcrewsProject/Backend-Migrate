package com.example.demo.model;

import com.example.demo.constant.ProjectStatus;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

// 프로젝트 엔티티
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "project")
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

    @Enumerated(value = EnumType.STRING)
    private ProjectStatus status;

    @Column(nullable = true)
    private int crewNumber;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
