package com.example.demo.service.projectVote.fwithdraw.history;

import com.example.demo.constant.VoteOptionDA;
import com.example.demo.model.projectVote.fwithdraw.history.VoteFWithdrawHistory;
import com.example.demo.repository.projectVote.fwithdraw.history.VFWithdrawHistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public interface VFWithdrawHistoryService {

    void toVoteFWithdrawHistoryEntity(Long project_vote_fw_id, Long voter_id, VoteOptionDA answer);

    VoteFWithdrawHistory findVFWHistoryByVoter(Long voteId, Long voterId);
}
