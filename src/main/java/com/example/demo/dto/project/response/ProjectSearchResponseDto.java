package com.example.demo.dto.project.response;

import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.model.project.Project;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ProjectSearchResponseDto {
    private Long projectId;
    private String name;
    private String subject;
    private TrustGradeResponseDto trustGrade;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public static ProjectSearchResponseDto of(Project project, TrustGradeResponseDto trustGradeResponseDto) {
        return ProjectSearchResponseDto.builder()
                .projectId(project.getId())
                .name(project.getName())
                .subject(project.getSubject())
                .trustGrade(trustGradeResponseDto)
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .build();
    }
}
