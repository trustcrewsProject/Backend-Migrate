package com.example.demo.service.milestone;

import com.example.demo.dto.milestone.request.MileStoneUpdateRequestDto;
import com.example.demo.dto.milestone.request.MilestoneUpdateContentRequestDto;
import com.example.demo.dto.milestone.request.MilestoneUpdateDateRequestDto;
import com.example.demo.dto.milestone.response.MilestoneReadResponseDto;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface MilestoneService {

    Milestone findById(Long id);

    Milestone save(Milestone milestone);

    List<Milestone> findMilestonesByProject(Project project);

    MilestoneReadResponseDto getOne(Long milestoneId);

    void update(Long milestoneId, MileStoneUpdateRequestDto mileStoneUpdateRequestDto);

    void delete(Long milestoneId);

    void updateContent(Long milestoneId, MilestoneUpdateContentRequestDto milestoneUpdateContentRequestDto);

    void updateDate(Long milestoneId, MilestoneUpdateDateRequestDto milestoneUpdateDateRequestDto);

    void deleteAllByProject(Project project);

    int countByProjectId(Long projectId);
}
