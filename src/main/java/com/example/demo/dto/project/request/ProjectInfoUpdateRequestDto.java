package com.example.demo.dto.project.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ProjectInfoUpdateRequestDto {

    @NotBlank(message = "프로젝트 ID(PK) 정보는 필수 요청 값입니다.")
    private Long projectId;
    private String projectName;
    private String subject;
    private Long trustGradeId;
    private LocalDate startDate;
    private LocalDate endDate;
}
