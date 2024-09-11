package com.example.demo.dto.board.Response;

import com.example.demo.dto.boardposition.BoardPositionDetailResponseDto;
import com.example.demo.dto.project.setting.response.ProjectSettingInfoResponseDto;
import com.example.demo.dto.user.response.UserSearchResponseDto;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.example.demo.model.board.Board;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class BoardSearchResponseDto {
    private Long boardId;
    private String title;
    private List<BoardPositionDetailResponseDto> boardPositions;
    private ProjectSettingInfoResponseDto project;
    private int boardPageView;
    private boolean recruitmentStatus;
    private UserSearchResponseDto user;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime createDate;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime updateDate;

    @QueryProjection
    public BoardSearchResponseDto(
            Long boardId,
            String title,
            List<BoardPositionDetailResponseDto> boardPositions,
            ProjectSettingInfoResponseDto projectDto,
            int boardPageView,
            boolean recruitmentStatus,
            UserSearchResponseDto user,
            LocalDateTime createDate,
            LocalDateTime updateDate) {
        this.boardId = boardId;
        this.title = title;
        this.boardPositions = boardPositions;
        this.project = projectDto;
        this.boardPageView = boardPageView;
        this.recruitmentStatus = recruitmentStatus;
        this.user = user;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public static BoardSearchResponseDto of(
            Board board,
            List<BoardPositionDetailResponseDto> boardPositions,
            ProjectSettingInfoResponseDto projectDto,
            UserSearchResponseDto userSearchResponseDto) {
        return BoardSearchResponseDto.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .boardPositions(boardPositions)
                .project(projectDto)
                .boardPageView(board.getPageView())
                .recruitmentStatus(board.isRecruitmentStatus())
                .user(userSearchResponseDto)
                .createDate(board.getCreateDate())
                .updateDate(board.getUpdateDate())
                .build();
    }
}
