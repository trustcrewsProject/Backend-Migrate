package com.example.demo.service.projectVote.fwithdraw;

import com.example.demo.constant.ProjectFWReason;
import com.example.demo.constant.VoteOptionDA;
import com.example.demo.constant.VoteResult;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.projectVote.fwithdraw.VoteFWithdraw;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface VFWithdrawService {
    VoteFWithdraw toVoteFWithdrawEntity(Long userId, ProjectMember fwMember, ProjectFWReason reason, Long projectId);
    VoteFWithdraw updateVoteDA(Long voteId, VoteOptionDA answer);
    VoteResult getProjectVoteResult(int maxVoteCount, int agrees, int disagrees);
    void endProjectVote(Long voteId);
    VoteFWithdraw findVFWById(Long voteId);
}
