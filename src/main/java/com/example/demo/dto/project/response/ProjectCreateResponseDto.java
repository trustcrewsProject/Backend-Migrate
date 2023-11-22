package com.example.demo.dto.project.response;

import com.example.demo.constant.ProjectStatus;
import java.time.LocalDateTime;

import com.example.demo.model.project.Project;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectCreateResponseDto {
    private String name;
    private String subject;
    private long trustGradeId;
    private long userId;
    private ProjectStatus status;
    private int crewNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static ProjectCreateResponseDto of(Project project) {
        return ProjectCreateResponseDto.builder()
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
