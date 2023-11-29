package com.example.demo.service.work;

import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkService {
    public Work findById(Long id);

    public List<Work> findWorksByProject(Project project);

    public Work findFirstByProjectAndUserOrderByProjectDesc(Project project, User user);
}
