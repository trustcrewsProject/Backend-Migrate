package com.example.demo.service.milestone;

import com.example.demo.model.milestone.Milestone;
import org.springframework.stereotype.Service;

@Service
public interface MilestoneService {
    public Milestone findById(Long id);
}
