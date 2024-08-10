package com.example.demo.service.projectVote.recruit;

import com.example.demo.constant.VoteOptionDA;
import com.example.demo.constant.VoteResult;
import com.example.demo.model.projectApply.ProjectApply;
import com.example.demo.model.projectVote.recruit.VoteRecruit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface VoteRecruitService {

    VoteRecruit createProjectVoteRecruit(ProjectApply projectApply);

    VoteRecruit updateVoteRecruitDA(Long voteId, VoteOptionDA answer);

    VoteResult getProjectVoteResult(int maxVoteCount, int agrees, int disagrees);

    void endProjectVote(Long voteId);
}
