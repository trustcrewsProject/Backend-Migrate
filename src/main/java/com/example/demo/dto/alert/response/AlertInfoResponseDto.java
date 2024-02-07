package com.example.demo.dto.alert.response;

import com.example.demo.constant.AlertType;
import com.example.demo.dto.position.response.PositionInfoResponseDto;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.example.demo.model.alert.Alert;
import com.example.demo.model.position.Position;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.Objects;

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

    private boolean checkedStatus;

    public AlertInfoResponseDto(Long alertId, Long projectId, Long checkUserId, Long sendUserId,
                                Long workId, Long milestoneId, PositionInfoResponseDto position,
                                String content, AlertType type, LocalDateTime createDate, LocalDateTime updateDate, boolean checkedStatus) {
        this.alertId = alertId;
        this.projectId = projectId;
        this.checkUserId = checkUserId;
        this.sendUserId = sendUserId;
        this.workId = Objects.nonNull(workId) ? workId : null;
        this.milestoneId = Objects.nonNull(milestoneId) ? milestoneId : null;
        this.position = Objects.nonNull(position.getPositionId()) ? position : null;
        this.content = content;
        this.type = type;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.checkedStatus = checkedStatus;
    }

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
                .checkedStatus(alert.isChecked_YN())
                .build();
    }
}
