package com.example.demo.service.milestone;

import com.example.demo.global.exception.customexception.MilestoneCustomException;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.repository.milestone.MileStoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MilestoneServiceImpl {
    private final MileStoneRepository mileStoneRepository;

    public Milestone findById(Long id){
        return mileStoneRepository.findById(id).orElseThrow(()-> MilestoneCustomException.NOT_FOUND_MILESTONE);
    }
}
