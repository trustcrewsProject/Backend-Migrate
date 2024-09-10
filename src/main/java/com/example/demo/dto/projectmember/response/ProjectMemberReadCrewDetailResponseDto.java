package com.example.demo.dto.projectmember.response;

import com.example.demo.constant.ProjectMemberAuth;
import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.projectmember.ProjectMemberAuthDto;
import com.example.demo.dto.user.response.UserCrewDetailResponseDto;
import com.example.demo.model.project.ProjectMember;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectMemberReadCrewDetailResponseDto {
    private Long projectMemberId;
    private Long projectId;
    private int projectCount;
    private UserCrewDetailResponseDto user;
    private ProjectMemberAuthDto<ProjectMemberAuth> projectMemberAuth;
    private PositionResponseDto position;
    private ProjectMemberStatus status;

    public static ProjectMemberReadCrewDetailResponseDto of(
            ProjectMember projectMember,
            int projectCount,
            UserCrewDetailResponseDto user,
            ProjectMemberAuthDto<ProjectMemberAuth> projectMemberAuth,
            PositionResponseDto position) {
        return ProjectMemberReadCrewDetailResponseDto.builder()
                .projectMemberId(projectMember.getId())
                .projectId(projectMember.getProject().getId())
                .projectCount(projectCount)
                .user(user)
                .projectMemberAuth(projectMemberAuth)
                .position(position)
                .status(projectMember.getStatus())
                .build();
    }
}
