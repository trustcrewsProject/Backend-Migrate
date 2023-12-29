package com.example.demo.dto.project.response;

import com.example.demo.constant.ProjectStatus;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.example.demo.model.project.Project;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectUpdateResponseDto {
    private Long projectId;
    private String name;
    private String subject;
    private long trustGradeId;
    private long userId;
    private ProjectStatus status;
    private int crewNumber;
    private LocalDate startDate;
    private LocalDate endDate;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime createDate;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime updateDate;

    public static ProjectUpdateResponseDto of(Project project) {
        return ProjectUpdateResponseDto.builder()
                .projectId(project.getId())
                .name(project.getName())
                .subject(project.getSubject())
                .trustGradeId(project.getTrustGrade().getId())
                .userId(project.getUser().getId())
                .status(project.getStatus())
                .crewNumber(project.getCrewNumber())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .createDate(project.getCreateDate())
                .updateDate(project.getUpdateDate())
                .build();
    }
}
