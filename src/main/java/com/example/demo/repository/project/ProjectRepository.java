package com.example.demo.repository.project;

import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findProjectsByUser(User user);
}
