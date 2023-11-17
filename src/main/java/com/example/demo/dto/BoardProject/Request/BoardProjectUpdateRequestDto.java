package com.example.demo.dto.BoardProject.Request;

import com.example.demo.dto.Board.Request.BoardUpdateRequestDto;
import com.example.demo.dto.Project.Request.ProjectUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardProjectUpdateRequestDto {
    private BoardUpdateRequestDto board;
    private ProjectUpdateRequestDto project;
}
