package com.example.demo.dto.projectAlert.vote.fwithdraw;

import com.example.demo.constant.ProjectFWReason;
import com.example.demo.constant.ProjectMemberAuth;
import com.example.demo.constant.VoteStatus;
import com.example.demo.dto.common.ConstantDto;
import com.example.demo.dto.projectmember.ProjectMemberAuthDto;
import com.example.demo.model.position.Position;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VAlertFWDetailResponseDto {
    private Long voteId;
    private ConstantDto<ProjectFWReason> reason;
    private int agrees;
    private int disagrees;
    private int maxVoteCount;
    private ConstantDto<VoteStatus> voteStatus;
    private ProjectMemberAuthDto<ProjectMemberAuth> fwMemberAuth;
    private Position fwMemberPosition;
    private String fwUserProfile;
    private String fwUserNickname;

    public static VAlertFWDetailResponseDto of(
            Long voteId,
            String reasonName,
            String reasonCode,
            int agrees,
            int disagrees,
            int maxVoteCount,
            VoteStatus voteStatus,
            ProjectMemberAuthDto<ProjectMemberAuth> fwMemberAuth,
            Position fwMemberPosition,
            String fwUserProfile,
            String fwUserNickname

    ){

       return VAlertFWDetailResponseDto.builder()
                .voteId(voteId)
                .reason(ConstantDto.of(reasonName, reasonCode))
                .agrees(agrees)
                .disagrees(disagrees)
                .maxVoteCount(maxVoteCount)
                .voteStatus(ConstantDto.of(voteStatus.getName(), voteStatus.getCode()))
                .fwMemberAuth(fwMemberAuth)
                .fwMemberPosition(fwMemberPosition)
                .fwUserProfile(fwUserProfile)
                .fwUserNickname(fwUserNickname)
                .build();
    }
}
