package com.example.demo.dto.project;

import com.example.demo.model.TrustGrade;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProjectUpdateRequestDto {
    private Long projectId;
    private String projectName;
    private String projectSubject;
    private TrustGrade projectTrust;
    private int projectCrewNumber;
    private LocalDateTime projectStartDate;
    private LocalDateTime projectEndDate;
}
