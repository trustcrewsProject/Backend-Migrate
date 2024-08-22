package com.example.demo.repository.projectVote.recruit;

import com.example.demo.constant.VoteOptionDA;
import com.example.demo.constant.VoteStatus;
import com.example.demo.dto.common.ConstantDto;
import com.example.demo.dto.projectVote.recruit.ProjectVoteRecruitResponseDto;
import com.example.demo.model.projectVote.recruit.QVoteRecruit;
import com.example.demo.model.projectVote.recruit.VoteRecruit;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class VoteRecruitRepositoryImpl implements VoteRecruitRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private static final QVoteRecruit qVoteRecruit = QVoteRecruit.voteRecruit;

    // 프로젝트 모집 투표 조회
    @Override
    public ProjectVoteRecruitResponseDto findProjectVoteRecruitItem(Long voteId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ProjectVoteRecruitResponseDto.class,
                                qVoteRecruit.id,
                                qVoteRecruit.projectApply.id,
                                Projections.constructor(
                                        ConstantDto.class,
                                        qVoteRecruit.voteStatus
                                ),
                                qVoteRecruit.agrees,
                                qVoteRecruit.disagrees,
                                qVoteRecruit.max_vote_count
                        ))
                .from(qVoteRecruit)
                .where(qVoteRecruit.id.eq(voteId))
                .fetchOne();
    }

    // 프로젝트 모집 투표 결과 업데이트
    @Override
    @Transactional
    public VoteRecruit updateVoteDA(Long voteId, VoteOptionDA answer) {
        if (answer.getCode().equals("VODA1001")) { // 찬성
            jpaQueryFactory
                    .update(qVoteRecruit)
                    .set(
                            qVoteRecruit.agrees,
                            qVoteRecruit.agrees.add(1))
                    .where(qVoteRecruit.id.eq(voteId))
                    .execute();
        } else { // 반대
            jpaQueryFactory
                    .update(qVoteRecruit)
                    .set(
                            qVoteRecruit.disagrees,
                            qVoteRecruit.disagrees.add(1))
                    .where(qVoteRecruit.id.eq(voteId))
                    .execute();
        }

        // 업데이트된 결과를 다시 조회하여 반환
        return jpaQueryFactory
                .selectFrom(qVoteRecruit)
                .where(qVoteRecruit.id.eq(voteId))
                .fetchOne();
    }

    @Override
    public void updateVoteStatus(Long voteId, VoteStatus voteStatus) {
        jpaQueryFactory
                .update(qVoteRecruit)
                .set(qVoteRecruit.voteStatus, voteStatus)
                .where(qVoteRecruit.id.eq(voteId))
                .execute();
    }


}
