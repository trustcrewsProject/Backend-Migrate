package com.example.demo.service.milestone;

import com.example.demo.global.exception.customexception.MilestoneCustomException;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.repository.milestone.MileStoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class MilestoneServiceImpl {
    private final MileStoneRepository mileStoneRepository;

    public Milestone findById(Long id){
        return mileStoneRepository.findById(id).orElseThrow(() -> MilestoneCustomException.NOT_FOUND_MILESTONE);
    }

    public Milestone save(Milestone milestone){
        return mileStoneRepository.save(milestone);
    }

    public List<Milestone> findMilestonesByProject(Project project){
        return mileStoneRepository.findMilestonesByProject(project).orElseThrow(() -> MilestoneCustomException.NOT_FOUND_MILESTONE);
    }
}
