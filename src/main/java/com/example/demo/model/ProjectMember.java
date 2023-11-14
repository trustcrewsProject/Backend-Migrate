package com.example.demo.model;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 프로젝트 구성원 엔티티
@Entity
@Table(name = "project_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProjectMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_member_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "project_member_auth_id")
    private ProjectMemberAuth projectMemberAuth;

    @Column(name = "project_member_status")
    private String status;

    @OneToOne
    @JoinColumn(name = "position_id")
    private Position position;

    @Builder
    public ProjectMember(Long id, Project project, User user, ProjectMemberAuth projectMemberAuth, String status, Position position) {
        this.id = id;
        this.project = project;
        this.user = user;
        this.projectMemberAuth = projectMemberAuth;
        this.status = status;
        this.position = position;
    }
}
