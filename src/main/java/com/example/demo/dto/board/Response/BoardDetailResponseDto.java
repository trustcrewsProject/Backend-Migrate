package com.example.demo.dto.board.Response;

import com.example.demo.dto.boardposition.BoardPositionDetailResponseDto;
import com.example.demo.dto.user.response.UserBoardDetailResponseDto;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.example.demo.model.board.Board;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardDetailResponseDto {
    private Long boardId;
    private Long projectId;
    private String title;
    private String content;
    private int pageView;
    private boolean recruitmentStatus;
    private UserBoardDetailResponseDto user;
    private String contact;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime createDate;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime updateDate;

    private List<BoardPositionDetailResponseDto> boardPositions;

    public static BoardDetailResponseDto of(
            Board board,
            UserBoardDetailResponseDto userBoardDetailResponseDto,
            List<BoardPositionDetailResponseDto> boardPositionDetailResponseDtos) {
        return BoardDetailResponseDto.builder()
                .boardId(board.getId())
                .projectId(board.getProject().getId())
                .title(board.getTitle())
                .content(board.getContent())
                .pageView(board.getPageView())
                .recruitmentStatus(board.isRecruitmentStatus())
                .user(userBoardDetailResponseDto)
                .contact(board.getContact())
                .createDate(board.getCreateDate())
                .updateDate(board.getUpdateDate())
                .boardPositions(boardPositionDetailResponseDtos)
                .build();
    }
}
