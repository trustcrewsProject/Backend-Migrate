package com.example.demo.dto.projectVote.fwithdraw;

import com.example.demo.constant.ProjectMemberAuth;
import com.example.demo.constant.VoteOptionDA;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VoteFWithdrawRequestDto {
    private Long projectId;
    private Long voteId;
    private Long fw_member_id;
    private ProjectMemberAuth fw_member_auth;
    private ProjectMemberAuth authMap;
    private VoteOptionDA answer;

}
