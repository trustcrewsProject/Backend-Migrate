package com.example.demo.service.projectVote.recruit;

import com.example.demo.model.projectApply.ProjectApply;
import com.example.demo.model.projectVote.ProjectVoteRecruit;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProjectVoteServiceRecruit {

    ProjectVoteRecruit createProjectVoteRecruit(ProjectApply projectApply);
}
