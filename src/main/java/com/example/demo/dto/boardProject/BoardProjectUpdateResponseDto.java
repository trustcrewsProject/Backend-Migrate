package com.example.demo.dto.boardProject;

import com.example.demo.dto.board.BoardUpdateResponseDto;
import com.example.demo.dto.project.ProjectUpdateResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardProjectUpdateResponseDto {
    private BoardUpdateResponseDto board;
    private ProjectUpdateResponseDto project;
}
