package com.example.demo.dto.board.response;

import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.example.demo.model.board.Board;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardCreateResponseDto {
    private String boardTitle;
    private String boardContent;
    private long boardPageView;
    private boolean recruitmentStatus;
    private long boardUserId;
    private String boardContact;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime createDate;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime updateDate;

    public static BoardCreateResponseDto of(Board board) {
        return BoardCreateResponseDto.builder()
                .boardTitle(board.getTitle())
                .boardContent(board.getContent())
                .boardPageView(board.getPageView())
                .recruitmentStatus(board.isRecruitmentStatus())
                .boardUserId(board.getUser().getId())
                .boardContact(board.getContact())
                .createDate(board.getCreateDate())
                .updateDate(board.getUpdateDate())
                .build();
    }
}
