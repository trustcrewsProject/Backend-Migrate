package com.example.demo.dto.project.request;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 프로젝트 개설 요청 DTO
@Getter
@AllArgsConstructor
public class ProjectCreateRequestDto {
    @NotBlank(message = "프로젝트명은 필수 입력 값입니다.")
    private String projectName;

    @NotBlank(message = "프로젝트 주제명은 필수 입력 값입니다.")
    private String projectSubject;

    @NotBlank(message = "프로젝트 신뢰등급은 필수 입력 값입니다.")
    private Long projectTrustId;

    @NotBlank(message = "프로젝트 인원 수는 필수 입력 값입니다.")
    private int projectCrewNumber;

    @NotBlank(message = "시작날짜는 필수 입력 값입니다.")
    private LocalDateTime projectStartDate;

    @NotBlank(message = "종료날짜는 필수 입력 값입니다.")
    private LocalDateTime projectEndDate;

    @NotBlank(message = "모집분야는 필수 입력 값입니다.")
    private List<Long> projectTechnologyIds;
}
