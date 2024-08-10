package com.example.demo.repository.projectVote.history;

import com.example.demo.model.projectVote.recruit.history.VoteRecruitHistory;

public interface VoteRecruitHistoryRepositoryCustom {

    VoteRecruitHistory findProjectVoteRecruitHistoryByVoter(Long voteId, Long voterId);

}
