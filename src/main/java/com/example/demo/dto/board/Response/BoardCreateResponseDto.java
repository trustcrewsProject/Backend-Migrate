package com.example.demo.dto.board.response;

import com.example.demo.model.board.Board;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardCreateResponseDto {
    private String boardTitle;
    private String boardContent;
    private long boardPageView;
    private boolean boardCompleteStatus;
    private long boardUserId;
    private String boardContact;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public BoardCreateResponseDto(Board board) {
        this.boardTitle = board.getTitle();
        this.boardContent = board.getContent();
        this.boardPageView = board.getPageView();
        this.boardCompleteStatus = board.isCompleteStatus();
        this.boardUserId = board.getUser().getId();
        this.boardContact = board.getContent();
        this.createDate = board.getCreateDate();
        this.updateDate = board.getUpdateDate();
    }
}
