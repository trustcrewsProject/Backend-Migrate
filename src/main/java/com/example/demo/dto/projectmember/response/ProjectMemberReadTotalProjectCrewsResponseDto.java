package com.example.demo.dto.projectmember.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProjectMemberReadTotalProjectCrewsResponseDto {
    private List<ProjectMemberReadProjectCrewsResponseDto> projectMembers;

    public static ProjectMemberReadTotalProjectCrewsResponseDto of(
            List<ProjectMemberReadProjectCrewsResponseDto> projectMembers
    ) {
        return ProjectMemberReadTotalProjectCrewsResponseDto.builder()
                .projectMembers(projectMembers)
                .build();
    }
}