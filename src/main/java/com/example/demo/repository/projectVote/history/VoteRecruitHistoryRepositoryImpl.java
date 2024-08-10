package com.example.demo.repository.projectVote.history;

import com.example.demo.model.projectVote.recruit.history.QVoteRecruitHistory;
import com.example.demo.model.projectVote.recruit.history.VoteRecruitHistory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VoteRecruitHistoryRepositoryImpl implements VoteRecruitHistoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private static final QVoteRecruitHistory qVoteRecruitHistory = QVoteRecruitHistory.voteRecruitHistory;

    @Override
    public VoteRecruitHistory findProjectVoteRecruitHistoryByVoter(Long voteId, Long voterId) {
        return jpaQueryFactory
                .selectFrom(qVoteRecruitHistory)
                .where(qVoteRecruitHistory.project_vote_id.eq(voteId),
                        qVoteRecruitHistory.voter_id.eq(voterId))
                .fetchOne();
    }
}
