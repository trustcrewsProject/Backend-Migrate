package com.example.demo.repository.project;

import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<List<Project>> findProjectsByUser(User user);
}
