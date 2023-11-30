package com.example.demo.service.milestone;

import com.example.demo.dto.milestone.request.MileStoneUpdateRequestDto;
import com.example.demo.dto.milestone.request.MilestoneUpdateContentRequestDto;
import com.example.demo.dto.milestone.request.MilestoneUpdateDateRequestDto;
import com.example.demo.dto.milestone.response.MilestoneReadResponseDto;
import com.example.demo.global.exception.customexception.ProjectCustomException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.repository.milestone.MileStoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface MilestoneService {

    public Milestone findById(Long id);

    public Milestone save(Milestone milestone);

    public List<Milestone> findMilestonesByProject(Project project);

    public MilestoneReadResponseDto getOne(Long milestoneId);

    public void update(Long milestoneId, MileStoneUpdateRequestDto mileStoneUpdateRequestDto);

    public void delete(Long milestoneId);

    public void updateContent(Long milestoneId, MilestoneUpdateContentRequestDto milestoneUpdateContentRequestDto);

    public void updateDate(Long milestoneId, MilestoneUpdateDateRequestDto milestoneUpdateDateRequestDto);
}
