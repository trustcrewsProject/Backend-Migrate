package com.example.demo.dto.projectVote.recruit;

import com.example.demo.constant.ProjectMemberAuth;
import com.example.demo.constant.VoteOptionDA;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectVoteRecruitRequestDto {
    private Long voteId;
    private Long applyId;
    private ProjectMemberAuth authMap;
    private VoteOptionDA answer;
}
