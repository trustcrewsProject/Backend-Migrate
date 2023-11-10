package com.example.demo.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

// 프로젝트 개설 요청 DTO
@Getter
@AllArgsConstructor
public class ProjectCreateRequestDto {

    @NotBlank(message = "프로젝트 신뢰등급은 필수 입력 값입니다.")
    private String trustGradeName;

    @NotBlank(message = "프로젝트명은 필수 입력 값입니다.")
    private String projectName;

    @NotBlank(message = "프로젝트 내용은 필수 입력 값입니다.")
    private String projectContent;

    private ProjectRecruitmentRequestDto recruitment;

}
