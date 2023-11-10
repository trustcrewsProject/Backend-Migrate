package com.example.demo.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

// 프로젝트 모집인원 요청 DTO
@Getter
@AllArgsConstructor
public class ProjectRecruitmentRequestDto {

    private int frontendRecruitment;

    private int backendRecruitment;

    private int designerRecruitment;

    private int publisherRecruitment;

    private int plannerRecruitment;
}
