package com.example.demo.dto.milestone;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

// 프로젝트 마일스톤 생성 요청 DTO
@Getter
@AllArgsConstructor
public class MilestoneCreateRequestDto {

    @NotBlank(message = "프로젝트 마일스톤 제목은 필수 입력 값입니다.")
    private String title;

    // 시작날짜
    @NotBlank(message = "마일스톤 시작날짜 설정은 필수입니다.")
    private String startDate;

    // 종료날짜
    @NotBlank(message = "마일스톤 종료날짜 설정은 필수입니다.")
    private String endDate;
}
