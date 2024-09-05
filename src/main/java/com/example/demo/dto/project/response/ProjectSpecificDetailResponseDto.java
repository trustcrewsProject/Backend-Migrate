package com.example.demo.dto.project.response;

import com.example.demo.constant.ProjectStatus;
import com.example.demo.dto.project.ProjectDetailAuthDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.example.demo.model.project.Project;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectSpecificDetailResponseDto {
    private Long projectId;
    private String name;
    private String subject;
    private TrustGradeResponseDto trustGrade;
    private ProjectStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectDetailAuthDto authMap;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime createDate;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime updateDate;

    public static ProjectSpecificDetailResponseDto of(
            Project project, TrustGradeResponseDto trustGradeDto) {
        return ProjectSpecificDetailResponseDto.builder()
                .projectId(project.getId())
                .name(project.getName())
                .subject(project.getSubject())
                .trustGrade(trustGradeDto)
                .status(project.getStatus())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .createDate(project.getCreateDate())
                .updateDate(project.getUpdateDate())
                .build();
    }
}
