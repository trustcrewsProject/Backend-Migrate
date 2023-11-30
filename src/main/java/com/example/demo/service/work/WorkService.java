package com.example.demo.service.work;

import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkService {

    public Work save(Work work);
    public Work findById(Long id);

    public List<Work> findWorksByProject(Project project);

    public Work findLastCompleteWork(Project project, User user, Boolean completeStatus);

    public List<Work> findWorksByProjectAndMilestone(Project project, Milestone milestone);
}
