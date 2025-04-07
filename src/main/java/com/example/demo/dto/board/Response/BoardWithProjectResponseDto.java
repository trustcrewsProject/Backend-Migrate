package com.example.demo.dto.board.Response;

import com.example.demo.dto.project.response.ProjectSummaryResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BoardWithProjectResponseDto {
    private BoardDetailResponseDto post;
    private ProjectSummaryResponseDto project;

    public static BoardWithProjectResponseDto of(
            ProjectSummaryResponseDto project,
            BoardDetailResponseDto board) {
        return BoardWithProjectResponseDto.builder()
                .post(board)
                .project(project)
                .build();
    }
}
