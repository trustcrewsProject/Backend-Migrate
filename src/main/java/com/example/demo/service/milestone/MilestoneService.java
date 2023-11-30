package com.example.demo.service.milestone;

import com.example.demo.global.exception.customexception.ProjectCustomException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.repository.milestone.MileStoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class MilestoneService {
    private final MileStoneRepository mileStoneRepository;

    public Milestone save(Milestone milestone){
        return mileStoneRepository.save(milestone);
    }

}
