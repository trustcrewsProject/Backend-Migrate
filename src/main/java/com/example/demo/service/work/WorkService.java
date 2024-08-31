package com.example.demo.service.work;

import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.model.project.Project;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface WorkService {

    Work save(Work work);

    Work findById(Long id);

    List<Work> findWorksByProject(Project project);

    Work findLastCompleteWork(Project project, User user);

    PaginationResponseDto findWorksByProjectAndMilestone(Long projectId, Long milestoneId, Pageable pageable);

    void delete(Work work);

    void deleteAllByProject(Project project);

    void deleteAllByMilestoneId(Long milestoneId);

    int countWorksByMilestoneId(Long milestoneId);
}
