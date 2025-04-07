package com.example.demo.dto.project.response;

import com.example.demo.dto.project.setting.response.ProjectSettingInfoResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.model.project.Project;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProjectSummaryResponseDto {
    private Long projectId;
    private String projectName;
    private String projectSubject;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<TechnologyStackInfoResponseDto> technologyStacks;

    public static ProjectSummaryResponseDto of(
            Project project,
            List<TechnologyStackInfoResponseDto> technologyStacks) {
        return ProjectSummaryResponseDto.builder()
                .projectId(project.getId())
                .projectName(project.getName())
                .projectSubject(project.getSubject())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .technologyStacks(technologyStacks)
                .build();
    }
}
