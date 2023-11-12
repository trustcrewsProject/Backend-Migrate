package com.example.demo.dto.boardPosition;

import com.example.demo.model.Board;
import com.example.demo.model.BoardPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardPositionCreateDto {
    private long boardId;
    private long positionId;
}
