package com.example.demo.dto.boardproject.Request;

import com.example.demo.dto.board.Request.BoardUpdateRequestDto;
import com.example.demo.dto.project.Request.ProjectUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardProjectUpdateRequestDto {
    private BoardUpdateRequestDto board;
    private ProjectUpdateRequestDto project;
}
