package com.example.demo.repository.projectVote.recruit.history;

import com.example.demo.model.projectVote.recruit.history.VoteRecruitHistory;

public interface VoteRecruitHistoryRepositoryCustom {

    VoteRecruitHistory findProjectVoteRecruitHistoryByVoter(Long voteId, Long voterId);

}
