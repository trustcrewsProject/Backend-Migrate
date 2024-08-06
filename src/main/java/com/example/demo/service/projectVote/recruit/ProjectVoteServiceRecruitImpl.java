package com.example.demo.service.projectVote.recruit;

import com.example.demo.model.project.Project;
import com.example.demo.model.projectApply.ProjectApply;
import com.example.demo.model.projectVote.ProjectVoteRecruit;
import com.example.demo.repository.project.ProjectMemberRepository;
import com.example.demo.repository.projectVote.ProjectVoteRecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectVoteServiceRecruitImpl implements ProjectVoteServiceRecruit {

    private final ProjectMemberRepository projectMemberRepository;

    private final ProjectVoteRecruitRepository projectVoteRecruitRepository;

    @Override
    public ProjectVoteRecruit createProjectVoteRecruit(ProjectApply projectApply) {
        Project project = projectApply.getProject();
        int max_vote_count = projectMemberRepository.countVotableProjectMember(project);
        ProjectVoteRecruit projectVoteRecruit = ProjectVoteRecruit.builder()
                .project(project)
                .projectApply(projectApply)
                .max_vote_count(max_vote_count)
                .build();

        return projectVoteRecruitRepository.save(projectVoteRecruit);

    }
}
