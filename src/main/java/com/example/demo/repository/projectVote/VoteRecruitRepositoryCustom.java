package com.example.demo.repository.projectVote;

import com.example.demo.constant.VoteOptionDA;
import com.example.demo.constant.VoteStatus;
import com.example.demo.dto.projectVote.recruit.ProjectVoteRecruitResponseDto;
import com.example.demo.model.projectVote.recruit.VoteRecruit;

public interface VoteRecruitRepositoryCustom {
    ProjectVoteRecruitResponseDto findProjectVoteRecruitItem(Long voteId);

    // 투표 찬/반 업데이트
    VoteRecruit updateVoteDA(Long voteId, VoteOptionDA answer);

    // 투표 종료
    void updateVoteStatus(Long voteId, VoteStatus voteStatus);


}
