package com.example.demo.dto.alert.response;

import com.example.demo.dto.position.response.PositionInfoResponseDto;
import com.example.demo.dto.project.response.ProjectSimpleInfoResponseDto;
import lombok.Getter;

@Getter
public class AlertSupportedProjectInfoResponseDto {

    private ProjectSimpleInfoResponseDto project;
    private PositionInfoResponseDto position;
    private Boolean supportResult;

    public AlertSupportedProjectInfoResponseDto(ProjectSimpleInfoResponseDto project, PositionInfoResponseDto position, Boolean supportResult) {
        this.project = project;
        this.position = position;
        this.supportResult = supportResult;
    }
}
