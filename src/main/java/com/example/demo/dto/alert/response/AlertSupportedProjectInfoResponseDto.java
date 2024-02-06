package com.example.demo.dto.alert.response;

import com.example.demo.dto.position.response.PositionInfoResponseDto;
import com.example.demo.dto.project.response.ProjectSimpleInfoResponseDto;
import lombok.Getter;

@Getter
public class AlertSupportedProjectInfoResponseDto {

    private Long alertId;
    private ProjectSimpleInfoResponseDto project;
    private PositionInfoResponseDto position;
    private Boolean supportResult;

    public AlertSupportedProjectInfoResponseDto(Long alertId, ProjectSimpleInfoResponseDto project, PositionInfoResponseDto position, Boolean supportResult) {
        this.alertId = alertId;
        this.project = project;
        this.position = position;
        this.supportResult = supportResult;
    }
}
