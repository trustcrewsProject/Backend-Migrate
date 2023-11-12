package com.example.demo.dto.boardProject;

import com.example.demo.dto.board.BoardCreateRequestDto;
import com.example.demo.dto.project.ProjectCreateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class BoardProjectCreateRequestDto {
    private BoardCreateRequestDto board;
    private ProjectCreateRequestDto project;
}
