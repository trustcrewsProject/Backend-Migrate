package com.example.demo.service.projectAlert.recruit;

import com.example.demo.model.project.alert.recruit.ProjectRecruitAlert;
import com.example.demo.model.projectVote.ProjectVoteRecruit;
import com.example.demo.repository.projectAlert.recruit.ProjectRecruitAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectAlertRecruitServiceImpl implements ProjectAlertRecruitService{
    private final ProjectRecruitAlertRepository projectRecruitAlertRepository;

    @Override
    public ProjectRecruitAlert toProjectRecruitAlertEntity(long projectId, ProjectVoteRecruit projectVoteRecruit) {
        ProjectRecruitAlert projectRecruitAlert = ProjectRecruitAlert.builder()
                .project_id(projectId)
                .projectVoteRecruit(projectVoteRecruit)
                .build();

        return projectRecruitAlertRepository.save(projectRecruitAlert);

    }
}
