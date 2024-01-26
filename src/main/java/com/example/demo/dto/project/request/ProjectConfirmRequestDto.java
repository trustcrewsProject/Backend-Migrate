package com.example.demo.dto.project.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectConfirmRequestDto {

    @NotNull(message = "알림 정보는 필수 요청 값입니다.")
    private Long alertId;

    @NotNull(message = "참여 수락 혹은 거절 정보는 필수 요청 값입니다.")
    private boolean confirmResult;
}
