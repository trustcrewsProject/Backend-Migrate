package com.example.demo.dto.projectmember.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProjectMemberWorksPaginationResponseDto {

    private List<ProjectMemberWorkWithTrustScoreResponseDto> content;
    private long totalPages;

    public static ProjectMemberWorksPaginationResponseDto of(List<ProjectMemberWorkWithTrustScoreResponseDto> content, long totalPages) {
        return ProjectMemberWorksPaginationResponseDto.builder()
                .content(content)
                .totalPages(totalPages)
                .build();
    }
}
