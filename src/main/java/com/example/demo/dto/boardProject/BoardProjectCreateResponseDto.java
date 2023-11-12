package com.example.demo.dto.boardProject;


import com.example.demo.dto.board.BoardCreateResponseDto;
import com.example.demo.dto.project.ProjectCreateResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardProjectCreateResponseDto {
    private BoardCreateResponseDto board;
    private ProjectCreateResponseDto project;
}
