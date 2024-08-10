package com.example.demo.dto.projectVote.recruit;

import com.example.demo.constant.VoteStatus;
import com.example.demo.dto.common.ConstantDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectVoteRecruitResponseDto {
    private Long voteId;
    private Long applyId;
    private VoteStatus voteStatus;
    private int agrees;
    private int disagrees;
    private int maxVoteCount;

    public ProjectVoteRecruitResponseDto(
            Long voteId,
            Long applyId,
            VoteStatus voteStatus,
            int agrees,
            int disagrees,
            int maxVoteCount
    ){
        this.voteId =voteId;
        this.applyId = applyId;
        this.voteStatus =voteStatus;
        this.agrees = agrees;
        this.disagrees = disagrees;
        this.maxVoteCount = maxVoteCount;
    }
}
