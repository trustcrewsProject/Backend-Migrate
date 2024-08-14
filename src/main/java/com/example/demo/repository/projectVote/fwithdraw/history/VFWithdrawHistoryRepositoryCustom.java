package com.example.demo.repository.projectVote.fwithdraw.history;

import com.example.demo.model.projectVote.fwithdraw.history.VoteFWithdrawHistory;

public interface VFWithdrawHistoryRepositoryCustom {
    VoteFWithdrawHistory findVFWHistoryByVoter(Long voteId, Long voterId);
}
