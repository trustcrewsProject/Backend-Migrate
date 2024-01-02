package com.example.demo.dto.project.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProjectPaginationResponseDto {

    private List<ProjectMeResponseDto> content;
    private long totalPages;

    public static ProjectPaginationResponseDto of(List<ProjectMeResponseDto> content, long totalPages) {
        return ProjectPaginationResponseDto.builder()
                .content(content)
                .totalPages(totalPages)
                .build();
    }
}
