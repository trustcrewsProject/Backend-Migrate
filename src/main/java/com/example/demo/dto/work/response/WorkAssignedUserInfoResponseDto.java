package com.example.demo.dto.work.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkAssignedUserInfoResponseDto {

    private Long projectMemberId;
    private String nickname;

    public WorkAssignedUserInfoResponseDto(Long projectMemberId, String nickname) {
        this.projectMemberId = projectMemberId;
        this.nickname = nickname;
    }

    public static WorkAssignedUserInfoResponseDto of(Long projectMemberId, String nickname) {
        return WorkAssignedUserInfoResponseDto.builder()
                .projectMemberId(projectMemberId)
                .nickname(nickname)
                .build();
    }
}
