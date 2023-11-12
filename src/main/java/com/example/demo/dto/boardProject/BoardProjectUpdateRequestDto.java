package com.example.demo.dto.boardProject;

import com.example.demo.dto.board.BoardCreateResponseDto;
import com.example.demo.dto.board.BoardUpdateRequestDto;
import com.example.demo.dto.project.ProjectCreateResponseDto;
import com.example.demo.dto.project.ProjectUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardProjectUpdateRequestDto {
    private BoardUpdateRequestDto board;
    private ProjectUpdateRequestDto project;
}
