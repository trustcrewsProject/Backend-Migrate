package com.example.demo.service.project;

import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProjectService {

    Project save(Project project);

    Project findById(Long id);

    List<Project> findProjectsByUser(User user);

    int countProjectsByUser(User user);
}
