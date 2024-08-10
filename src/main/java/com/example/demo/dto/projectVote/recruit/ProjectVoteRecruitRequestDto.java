package com.example.demo.dto.projectVote.recruit;

import com.example.demo.constant.VoteOptionDA;
import com.example.demo.dto.project.ProjectDetailAuthDto;
import com.example.demo.model.projectApply.ProjectApply;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectVoteRecruitRequestDto {
    private Long voteId;
    private Long applyId;
    private ProjectDetailAuthDto authMap;
    private VoteOptionDA answer;
}
