package com.example.demo.repository.projectApply;

import com.example.demo.constant.ProjectApplyStatus;
import com.example.demo.dto.projectApply.ProjectApplyResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProjectApplyRepositoryCustom {

    List<ProjectApplyResponseDto> findProjectAppliesByUserId(Long userId, Pageable pageable);

    Long countProjectAppliesByUserId(Long userId);

    Long countUserProjectApplying(Long projectId, Long userId);

    void udpateProjectApplyStatus(Long applyId, ProjectApplyStatus projectApplyStatus);

}
