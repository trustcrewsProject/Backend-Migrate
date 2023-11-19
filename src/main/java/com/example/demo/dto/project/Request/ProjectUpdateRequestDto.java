package com.example.demo.dto.project.Request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProjectUpdateRequestDto {
    private Long projectId;
    private String projectName;
    private String projectSubject;
    private Long projectTrustId;
    private int projectCrewNumber;
    private LocalDateTime projectStartDate;
    private LocalDateTime projectEndDate;
}
