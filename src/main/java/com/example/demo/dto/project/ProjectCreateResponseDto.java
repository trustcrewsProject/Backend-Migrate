package com.example.demo.dto.project;

import com.example.demo.constant.ProjectStatus;
import com.example.demo.model.Project;
import com.example.demo.model.TrustGrade;
import com.example.demo.model.User;

import java.time.LocalDateTime;

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

    public ProjectCreateResponseDto(Project project){
        this.name = project.getName();
        this.subject = project.getSubject();
        this.trustGradeId = project.getTrustGrade().getId();
        this.userId = project.getUser().getId();
        this.status = project.getStatus();
        this.crewNumber = project.getCrewNumber();
        this.startDate = project.getStartDate();
        this.endDate = project.getEndDate();
        this.createDate = project.getCreateDate();
        this.updateDate = project.getUpdateDate();
    }
}
