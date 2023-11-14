package com.example.demo.model;

import javax.persistence.*;
import lombok.Getter;

// 프로젝트 관리 권한 엔티티
@Entity
@Table(name = "project_manage_auth")
@Getter
public class ProjectMemberAuth {

    @Id
    @Column(name = "project_member_auth_id")
    private Long id;

    @Column(name = "project_manage_auth_name")
    private String name;

    @Column(name = "milestone_change_YN")
    private boolean milestoneChangeYN;

    @Column(name = "work_change_yn")
    private boolean workChangeYN;
}
