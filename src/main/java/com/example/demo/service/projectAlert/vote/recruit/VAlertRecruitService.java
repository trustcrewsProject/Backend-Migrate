package com.example.demo.service.projectAlert.vote.recruit;

import com.example.demo.dto.projectAlert.vote.recruit.ProjectRecruitAlertResponseDto;
import com.example.demo.model.project.alert.vote.VAlertRecruit;
import com.example.demo.model.projectVote.recruit.VoteRecruit;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface VAlertRecruitService {

    VAlertRecruit toProjectRecruitAlertEntity(Long project_id, VoteRecruit voteRecruit, String alertContents);

    Long countProjectRecruitAlerts(Long projectId);

    List<ProjectRecruitAlertResponseDto> findProjectRecruitAlertLists(Long projectId, Pageable pageable);
}
