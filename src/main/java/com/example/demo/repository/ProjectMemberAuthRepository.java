package com.example.demo.repository;

import com.example.demo.model.ProjectMemberAuth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberAuthRepository extends JpaRepository<ProjectMemberAuth, Long> {
}
