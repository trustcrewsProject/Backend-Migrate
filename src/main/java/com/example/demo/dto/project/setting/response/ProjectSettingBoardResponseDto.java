package com.example.demo.dto.project.setting.response;

import com.example.demo.dto.boardposition.BoardPositionDetailResponseDto;
import com.example.demo.model.board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProjectSettingBoardResponseDto {
    private Long boardId;
    private String title;
    private String content;
    private boolean recruitmentStatus;
    private String contact;
    private List<BoardPositionDetailResponseDto> boardPositions;

    public static ProjectSettingBoardResponseDto of(
            Board board,
            List<BoardPositionDetailResponseDto> boardPositionDetailResponseDtos) {
        return ProjectSettingBoardResponseDto.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .recruitmentStatus(board.isRecruitmentStatus())
                .contact(board.getContact())
                .boardPositions(boardPositionDetailResponseDtos)
                .build();
    }
}
