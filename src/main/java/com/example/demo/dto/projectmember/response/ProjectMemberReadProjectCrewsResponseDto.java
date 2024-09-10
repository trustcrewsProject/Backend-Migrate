package com.example.demo.dto.projectmember.response;

import com.example.demo.constant.ProjectMemberAuth;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.projectmember.ProjectMemberAuthDto;
import com.example.demo.dto.user.response.UserReadProjectCrewResponseDto;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.example.demo.model.project.ProjectMember;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectMemberReadProjectCrewsResponseDto {
    private Long projectMemberId;
    private UserReadProjectCrewResponseDto user;
    private ProjectMemberAuthDto<ProjectMemberAuth> projectMemberAuth;
    private PositionResponseDto position;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime lastWorkDate;

    public static ProjectMemberReadProjectCrewsResponseDto of(
            ProjectMember projectMember,
            UserReadProjectCrewResponseDto user,
            ProjectMemberAuthDto<ProjectMemberAuth> projectMemberAuth,
            PositionResponseDto position,
            LocalDateTime lastWorkDate) {

        return ProjectMemberReadProjectCrewsResponseDto.builder()
                .projectMemberId(projectMember.getId())
                .user(user)
                .projectMemberAuth(projectMemberAuth)
                .position(position)
                .lastWorkDate(lastWorkDate)
                .build();
    }
}
