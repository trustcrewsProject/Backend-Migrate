package com.example.demo.repository.projectAlert.vote.recruit;

import com.example.demo.dto.projectAlert.vote.recruit.ProjectRecruitAlertResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VAlertRecruitRepositoryCustom {
    List<ProjectRecruitAlertResponseDto> findProjectRecruitAlertList(Long projectId, Pageable pageable);

    Long countProjectRecruitAlerts(Long projectId);

//    ProjectRecruitAlertDetailResponseDto findProjectRecruitAlertDetail(Long alertId);


}
