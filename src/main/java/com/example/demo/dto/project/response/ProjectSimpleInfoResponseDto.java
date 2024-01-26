package com.example.demo.dto.project.response;

import lombok.Getter;

@Getter
public class ProjectSimpleInfoResponseDto {

    private Long projectId;
    private String projectName;

    public ProjectSimpleInfoResponseDto(Long projectId, String projectName) {
        this.projectId = projectId;
        this.projectName = projectName;
    }
}
