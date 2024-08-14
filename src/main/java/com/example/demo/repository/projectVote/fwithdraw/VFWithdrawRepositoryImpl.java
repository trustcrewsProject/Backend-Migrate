package com.example.demo.repository.projectVote.fwithdraw;

import com.example.demo.constant.VoteOptionDA;
import com.example.demo.constant.VoteStatus;
import com.example.demo.model.projectVote.fwithdraw.QVoteFWithdraw;
import com.example.demo.model.projectVote.fwithdraw.VoteFWithdraw;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class VFWithdrawRepositoryImpl implements VFWithdrawRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QVoteFWithdraw qVoteFWithdraw = QVoteFWithdraw.voteFWithdraw;

    @Override
    public VoteFWithdraw findProcessingVFWByProjectId(Long projectId) {
        return jpaQueryFactory
                .selectFrom(qVoteFWithdraw)
                .where(qVoteFWithdraw.project_id.eq(projectId),
                        qVoteFWithdraw.voteStatus.eq(VoteStatus.VSTAT1001))
                .fetchOne();
    }

    @Override
    public VoteFWithdraw findVFWById(Long voteId) {
        return jpaQueryFactory
                .selectFrom(qVoteFWithdraw)
                .where(qVoteFWithdraw.id.eq(voteId))
                .fetchOne();
    }

    @Override
    @Transactional
    public VoteFWithdraw updateVoteDA(Long voteId, VoteOptionDA answer) {
        if (answer.equals(VoteOptionDA.VODA1001)) { // 찬성
            jpaQueryFactory
                    .update(qVoteFWithdraw)
                    .set(qVoteFWithdraw.agrees, qVoteFWithdraw.agrees.add(1))
                    .where(qVoteFWithdraw.id.eq(voteId))
                    .execute();
        } else { // 반대
            jpaQueryFactory
                    .update(qVoteFWithdraw)
                    .set(qVoteFWithdraw.disagrees, qVoteFWithdraw.disagrees.add(1))
                    .where(qVoteFWithdraw.id.eq(voteId))
                    .execute();
        }

        // 업데이트된 결과 반환
        return jpaQueryFactory
                .selectFrom(qVoteFWithdraw)
                .where(qVoteFWithdraw.id.eq(voteId))
                .fetchOne();
    }

    @Override
    public void updateVoteStatus(Long voteId, VoteStatus voteStatus) {
        jpaQueryFactory
                .update(qVoteFWithdraw)
                .set(qVoteFWithdraw.voteStatus, voteStatus)
                .where(qVoteFWithdraw.id.eq(voteId))
                .execute();
    }
}
