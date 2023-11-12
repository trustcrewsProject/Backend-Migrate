package com.example.demo.dto.board;


import com.example.demo.dto.project.ProjectCreateResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardProjectCreateResponseDto {
    private BoardCreateResponseDto board;
    private ProjectCreateResponseDto project;
}
