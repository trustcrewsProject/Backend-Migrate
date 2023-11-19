package com.example.demo.dto.boardproject.Request;

import com.example.demo.dto.board.Request.BoardCreateRequestDto;
import com.example.demo.dto.project.Request.ProjectCreateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardProjectCreateRequestDto {
    private BoardCreateRequestDto board;
    private ProjectCreateRequestDto project;
}
