package com.example.demo.service.projectAlert.recruit;

import com.example.demo.model.project.alert.recruit.ProjectRecruitAlert;
import com.example.demo.model.projectVote.ProjectVoteRecruit;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProjectAlertRecruitService {

    ProjectRecruitAlert toProjectRecruitAlertEntity(long projectId, ProjectVoteRecruit projectVoteRecruit);

}
