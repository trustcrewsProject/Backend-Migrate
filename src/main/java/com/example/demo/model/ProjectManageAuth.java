package com.example.demo.model;

import lombok.Getter;

import javax.persistence.*;

// 프로젝트 관리 권한 엔티티
@Entity
@Table(name = "project_manage_auth")
@Getter
public class ProjectManageAuth {

    @Id
    @Column(name = "project_manage_auth_code")
    private String code;

    @Column(name = "project_manage_auth_name")
    private String name;

    @Column(name = "milestone_change_yn")
    private boolean milestoneChangeStatus;

    @Column(name = "work_change_yn")
    private boolean workChangeStatus;
}
