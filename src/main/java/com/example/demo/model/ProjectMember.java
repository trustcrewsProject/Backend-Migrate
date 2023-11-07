package com.example.demo.model;

import javax.persistence.*;

// 프로젝트 구성원 엔티티
@Entity
@Table(name = "project_member")
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
    @JoinColumn(name = "project_member_role_id")
    private ProjectMemberRole projectRole;

    @Column(name = "project_member_status")
    private String status;

    @OneToOne
    @JoinColumn(name = "project_manage_auth_code")
    private ProjectManageAuth authCode;
}
