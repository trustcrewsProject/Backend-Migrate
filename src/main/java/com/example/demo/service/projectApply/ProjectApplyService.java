package com.example.demo.service.projectApply;

import com.example.demo.constant.ProjectApplyStatus;
import com.example.demo.dto.projectApply.ProjectApplyResponseDto;
import com.example.demo.model.projectApply.ProjectApply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface ProjectApplyService {
    ProjectApply toProjectApplyEntity(long userId, long projectId, long positionId, String apply_message);

    List<ProjectApplyResponseDto> findProjectAppliesByUserId(Long userId, Pageable pageable);

    Long countProjectAppliesByUserId(Long userId);

    public void udpateProjectApplyStatus(Long applyId, ProjectApplyStatus projectApplyStatus);
}
