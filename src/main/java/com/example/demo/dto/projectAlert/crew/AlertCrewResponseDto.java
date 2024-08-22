package com.example.demo.dto.projectAlert.crew;

import com.example.demo.constant.ProjectAlertType;
import com.example.demo.dto.common.ConstantDepthDto;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AlertCrewResponseDto {
    private Long alertId;
    private Long projectId;
    private ConstantDepthDto<ProjectAlertType> alertType;
    private String contents;
    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime createDate;

    public AlertCrewResponseDto(
            Long alertId,
            Long projectId,
            ConstantDepthDto<ProjectAlertType> alertType,
            String contents,
            LocalDateTime createDate

    ) {
        this.alertId = alertId;
        this.projectId = projectId;
        this.alertType = alertType;
        this.contents = contents;
        this.createDate = createDate;
    }
}
