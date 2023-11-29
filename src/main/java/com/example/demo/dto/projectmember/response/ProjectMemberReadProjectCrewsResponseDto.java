package com.example.demo.dto.projectmember.response;

import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.user.response.UserInfoResponseDto;
import com.example.demo.dto.user.response.UserReadProjectCrewResponseDto;
import com.example.demo.model.project.ProjectMember;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProjectMemberReadProjectCrewsResponseDto {
    private Long projectMemberId;
    private UserReadProjectCrewResponseDto user;
    private ProjectMemberAuthResponseDto projectMemberAuth;
    private PositionResponseDto position;

    public static ProjectMemberReadProjectCrewsResponseDto of(
            ProjectMember projectMember,
            UserReadProjectCrewResponseDto user,
            ProjectMemberAuthResponseDto projectMemberAuth,
            PositionResponseDto position
    ) {

        return ProjectMemberReadProjectCrewsResponseDto.builder()
                .projectMemberId(projectMember.getId())
                .user(user)
                .projectMemberAuth(projectMemberAuth)
                .position(position)
                .build();
    }
}
