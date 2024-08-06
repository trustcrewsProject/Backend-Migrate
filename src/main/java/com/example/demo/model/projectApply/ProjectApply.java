package com.example.demo.model.projectApply;

import com.example.demo.constant.ProjectApplyStatus;
import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.position.Position;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "project_applicants")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProjectApply extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_apply_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;

    private String apply_message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "project_applicant_status")
    private ProjectApplyStatus status;

    @Builder
    public ProjectApply(User user, Project project, Position position, String apply_message) {
        this.user = user;
        this.project = project;
        this.position = position;
        this.apply_message = apply_message;
        this.status = ProjectApplyStatus.PAS1001;
    }
}
