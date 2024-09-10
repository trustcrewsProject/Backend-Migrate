package com.example.demo.dto.project.setting.request;

import com.example.demo.constant.ProjectMemberAuth;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProjectSettingInfoUpdRequestDto {
    private Long projectId;
    private ProjectMemberAuth authMap;
    private String projectName;

    private String projectSubject;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<Long> technologyIds;
}
