package com.example.demo.dto.board.response;

import com.example.demo.dto.project.response.ProjectSearchResponseDto;
import com.example.demo.dto.user.response.UserSearchResponseDto;
import com.example.demo.model.board.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BoardSearchResponseDto {
    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private ProjectSearchResponseDto project;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int boardPageView;
    private boolean boardCompleteStatus;
    private UserSearchResponseDto user;
    private String boardContact;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static BoardSearchResponseDto of(Board board, ProjectSearchResponseDto boardProjectSearchResponseDto, UserSearchResponseDto userSearchResponseDto) {
        return BoardSearchResponseDto.builder()
                .boardId(board.getId())
                .boardTitle(board.getTitle())
                .boardContent(board.getContent())
                .project(boardProjectSearchResponseDto)
                .boardPageView(board.getPageView())
                .boardCompleteStatus(board.isCompleteStatus())
                .user(userSearchResponseDto)
                .boardContent(board.getContent())
                .createDate(board.getCreateDate())
                .updateDate(board.getUpdateDate())
                .build();
    }
}
