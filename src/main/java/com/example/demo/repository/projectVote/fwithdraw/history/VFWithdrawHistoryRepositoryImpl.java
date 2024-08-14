package com.example.demo.repository.projectVote.fwithdraw.history;

import com.example.demo.model.projectVote.fwithdraw.history.QVoteFWithdrawHistory;
import com.example.demo.model.projectVote.fwithdraw.history.VoteFWithdrawHistory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VFWithdrawHistoryRepositoryImpl implements VFWithdrawHistoryRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private final QVoteFWithdrawHistory qVoteFWithdrawHistory = QVoteFWithdrawHistory.voteFWithdrawHistory;

    @Override
    public VoteFWithdrawHistory findVFWHistoryByVoter(Long voteId, Long voterId) {
        return jpaQueryFactory
                .selectFrom(qVoteFWithdrawHistory)
                .where(qVoteFWithdrawHistory.project_vote_fw_id.eq(voteId),
                        qVoteFWithdrawHistory.voter_id.eq(voterId))
                .fetchOne();
    }
}
