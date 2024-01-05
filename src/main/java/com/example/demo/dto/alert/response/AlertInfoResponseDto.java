package com.example.demo.dto.alert.response;

import com.example.demo.constant.AlertType;
import com.example.demo.dto.position.response.PositionInfoResponseDto;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.position.Position;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
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
    private PositionInfoResponseDto position;
    private String content;
    private AlertType type;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime createDate;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime updateDate;

    public static AlertInfoResponseDto of(Alert alert, Position position) {
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
                .position(
                        ObjectUtils.isEmpty(position)
                                ? null
                                : PositionInfoResponseDto.of(position.getId(), position.getName()))
                .content(alert.getContent())
                .type(alert.getType())
                .createDate(alert.getCreateDate())
                .updateDate(alert.getUpdateDate())
                .build();
    }
}
