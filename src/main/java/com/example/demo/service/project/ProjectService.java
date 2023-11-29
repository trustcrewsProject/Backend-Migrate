package com.example.demo.service.project;

import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {

    public Project save(Project project);

    public Project findById(Long id);
    public List<Project> findProjectsByUser(User user);
    public int countProjectsByUser(User user);
}
