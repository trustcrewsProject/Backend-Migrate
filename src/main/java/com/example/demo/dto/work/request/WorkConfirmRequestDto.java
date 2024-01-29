package com.example.demo.dto.work.request;

import com.example.demo.constant.ProgressStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class WorkConfirmRequestDto {

    @NotBlank(message = "알림 정보는 필수 요청 값입니다.")
    private Long alertId;

    @NotBlank(message = "신뢰점수 부여(1) 혹은 차감(2)은 필수 요청 값입니다.")
    private Long scoreTypeId;

    public void updateScoreTypeId(Long scoreTypeId) {
        this.scoreTypeId = scoreTypeId;
    }
}
