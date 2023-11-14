package com.example.demo.model;

import javax.persistence.*;
import lombok.Getter;

// 프로젝트 구성원 역할 엔티티
@Entity
@Table(name = "project_member_role")
@Getter
public class ProjectMemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "project_member_role_id")
    private Long id;

    @Column(name = "project_member_role_name")
    private String name;
}
