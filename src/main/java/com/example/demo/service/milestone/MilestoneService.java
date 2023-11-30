package com.example.demo.service.milestone;

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

}
