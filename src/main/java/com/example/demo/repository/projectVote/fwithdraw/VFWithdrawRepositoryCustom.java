package com.example.demo.repository.projectVote.fwithdraw;

import com.example.demo.constant.VoteOptionDA;
import com.example.demo.constant.VoteStatus;
import com.example.demo.model.projectVote.fwithdraw.VoteFWithdraw;

public interface VFWithdrawRepositoryCustom {
    // 프로젝트의 진행중인 강제투표 조회
    VoteFWithdraw findProcessingVFWByProjectId(Long projectId);

    VoteFWithdraw findVFWById(Long voteId);

    VoteFWithdraw updateVoteDA(Long voteId, VoteOptionDA answer);

    void updateVoteStatus(Long voteId, VoteStatus voteStatus);


}
