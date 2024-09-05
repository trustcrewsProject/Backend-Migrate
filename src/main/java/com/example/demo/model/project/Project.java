package com.example.demo.model.project;

import com.example.demo.constant.ProjectStatus;
import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.model.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProjectMember> projectMembers = new ArrayList<>();

    @OneToMany(mappedBy = "project", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ProjectTechnology> projectTechnologies = new ArrayList<>();

    @Builder
    public Project(
            String name,
            String subject,
            TrustGrade trustGrade,
            User user,
            ProjectStatus status,
            LocalDate startDate,
            LocalDate endDate) {
        this.name = name;
        this.subject = subject;
        this.trustGrade = trustGrade;
        this.user = user;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateProject(String name, String subject, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void endProject() {
        this.status = ProjectStatus.FINISH;
    }

    public void changeProjectMembers(List<ProjectMember> projectMembers) {
        this.projectMembers.clear();
        this.projectMembers.addAll(projectMembers);
    }

    public void changeProjectTechnologys(List<ProjectTechnology> projectTechnologies) {
        this.projectTechnologies.clear();
        this.projectTechnologies.addAll(projectTechnologies);
    }

    public void removeProjectMembers() {
        this.projectMembers.clear();
    }

    public void removeProjectTechnologies() {
        this.projectTechnologies.clear();
    }
}
