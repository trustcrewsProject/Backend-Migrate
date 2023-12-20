package com.example.demo.dto.projectmember.response;

import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.user.response.UserReadProjectCrewResponseDto;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.example.demo.model.project.ProjectMember;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectMemberReadProjectCrewsResponseDto {
    private Long projectMemberId;
    private UserReadProjectCrewResponseDto user;
    private ProjectMemberAuthResponseDto projectMemberAuth;
    private PositionResponseDto position;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime lastWorkDate;

    public static ProjectMemberReadProjectCrewsResponseDto of(
            ProjectMember projectMember,
            UserReadProjectCrewResponseDto user,
            ProjectMemberAuthResponseDto projectMemberAuth,
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
