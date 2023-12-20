package com.example.demo.dto.alert.response;

import com.example.demo.constant.AlertType;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.example.demo.model.alert.Alert;
import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

@Getter
@Builder
public class AlertInfoResponseDto {
    private Long alertId;
    private Long projectId;
    private Long checkUserId;
    private Long sendUserId;
    private Long workId;
    private Long milestoneId;
    private Long positionId;
    private String content;
    private AlertType type;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime createDate;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime updateDate;

    public static AlertInfoResponseDto of(Alert alert) {
        return AlertInfoResponseDto.builder()
                .alertId(alert.getId())
                .projectId(alert.getProject().getId())
                .checkUserId(alert.getCheckUser().getId())
                .sendUserId(alert.getSendUser().getId())
                .workId(ObjectUtils.isEmpty(alert.getWork()) ? null : alert.getWork().getId())
                .milestoneId(
                        ObjectUtils.isEmpty(alert.getMilestone())
                                ? null
                                : alert.getMilestone().getId())
                .positionId(
                        ObjectUtils.isEmpty(alert.getPosition())
                                ? null
                                : alert.getPosition().getId())
                .content(alert.getContent())
                .type(alert.getType())
                .createDate(alert.getCreateDate())
                .updateDate(alert.getUpdateDate())
                .build();
    }
}
