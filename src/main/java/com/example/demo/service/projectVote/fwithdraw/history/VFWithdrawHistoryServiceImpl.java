package com.example.demo.service.projectVote.fwithdraw.history;

import com.example.demo.constant.VoteOptionDA;
import com.example.demo.model.projectVote.fwithdraw.history.VoteFWithdrawHistory;
import com.example.demo.repository.projectVote.fwithdraw.history.VFWithdrawHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VFWithdrawHistoryServiceImpl implements VFWithdrawHistoryService {

    private final VFWithdrawHistoryRepository vfWithdrawHistoryRepository;

    @Override
    public void toVoteFWithdrawHistoryEntity(Long project_vote_fw_id, Long voter_id, VoteOptionDA answer) {
        VoteFWithdrawHistory voteFWithdrawHistory = VoteFWithdrawHistory.builder()
                .project_vote_fw_id(project_vote_fw_id)
                .voter_id(voter_id)
                .answer(answer)
                .build();

        vfWithdrawHistoryRepository.save(voteFWithdrawHistory);
    }

    @Override
    public VoteFWithdrawHistory findVFWHistoryByVoter(Long voteId, Long voterId) {
        return vfWithdrawHistoryRepository.findVFWHistoryByVoter(voteId, voterId);
    }
}
