package com.example.demo.service.projectVote.recruit;

import com.example.demo.constant.VoteOptionDA;
import com.example.demo.constant.VoteResult;
import com.example.demo.constant.VoteStatus;
import com.example.demo.model.project.Project;
import com.example.demo.model.projectApply.ProjectApply;
import com.example.demo.model.projectVote.recruit.VoteRecruit;
import com.example.demo.repository.project.ProjectMemberRepository;
import com.example.demo.repository.projectVote.recruit.VoteRecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VoteRecruitServiceImpl implements VoteRecruitService {

    private final ProjectMemberRepository projectMemberRepository;

    private final VoteRecruitRepository voteRecruitRepository;

    @Override
    public VoteRecruit createProjectVoteRecruit(ProjectApply projectApply) {
        Project project = projectApply.getProject();
        int max_vote_count = projectMemberRepository.countVotableProjectMember(project);
        VoteRecruit voteRecruit = VoteRecruit.builder()
                .project(project)
                .projectApply(projectApply)
                .max_vote_count(max_vote_count)
                .build();

        return voteRecruitRepository.save(voteRecruit);
    }

    @Override
    public VoteRecruit updateVoteRecruitDA(Long voteId, VoteOptionDA answer) {
        return voteRecruitRepository.updateVoteDA(voteId, answer);
    }

    @Override
    public VoteResult getProjectVoteResult(int maxVoteCount, int agrees, int disagrees){
        int maxCountForOne = Math.round(maxVoteCount / 2);
        boolean isFulfilled =
                agrees + disagrees == maxVoteCount || agrees > maxCountForOne || disagrees > maxCountForOne;
        if (!isFulfilled) {
            return VoteResult.NOT_FULFILLED;
        }
        return agrees > maxCountForOne ? VoteResult.AGREE : VoteResult.DISAGREE;
    }

    @Override
    public void endProjectVote(Long voteId) {
        voteRecruitRepository.updateVoteStatus(voteId, VoteStatus.VSTAT1002);
    }


}
