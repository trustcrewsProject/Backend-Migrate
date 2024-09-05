package com.example.demo.dto.project.response;

import com.example.demo.constant.ProjectStatus;
import com.example.demo.dto.projectmember.response.MyProjectMemberResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.example.demo.model.project.Project;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectMeResponseDto {
    private Long projectId;
    private String name;
    private String subject;
    private TrustGradeResponseDto trustGrade;
    private List<MyProjectMemberResponseDto> members;
    private ProjectStatus status;
    private LocalDate startDate;
    private LocalDate endDate;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime createDate;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime updateDate;

    public static ProjectMeResponseDto of(
            Project project,
            TrustGradeResponseDto trustGradeDto,
            List<MyProjectMemberResponseDto> myProjectMemberResponseDtos) {
        return ProjectMeResponseDto.builder()
                .projectId(project.getId())
                .name(project.getName())
                .subject(project.getSubject())
                .trustGrade(trustGradeDto)
                .members(myProjectMemberResponseDtos)
                .status(project.getStatus())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .createDate(project.getCreateDate())
                .updateDate(project.getUpdateDate())
                .build();
    }
}
