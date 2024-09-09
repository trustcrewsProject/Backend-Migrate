package com.example.demo.dto.projectVote.fwithdraw;

import com.example.demo.constant.VoteOptionDA;
import com.example.demo.dto.project.ProjectDetailAuthDto;
import com.example.demo.model.project.ProjectMemberAuth;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoteFWithdrawRequestDto {
    private Long projectId;
    private Long voteId;
    private Long fw_member_id;
    private Long fw_member_auth_id;
    private ProjectDetailAuthDto authMap;
    private VoteOptionDA answer;

}
