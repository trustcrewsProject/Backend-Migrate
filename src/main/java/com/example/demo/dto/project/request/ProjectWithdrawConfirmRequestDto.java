package com.example.demo.dto.project.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class ProjectWithdrawConfirmRequestDto {

    @NotBlank(message = "탈퇴신청 알림 정보는 필수 요청 값입니다.")
    private Long alertId;

    @NotBlank(message = "탈퇴 컨펌 정보는 필수 요청 값입니다.")
    private boolean withdrawConfirm;
}
