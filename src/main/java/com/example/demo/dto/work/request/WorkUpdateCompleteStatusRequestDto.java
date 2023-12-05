package com.example.demo.dto.work.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkUpdateCompleteStatusRequestDto {
    @NotNull(message = "완료상태는 필수입니다.")
    private Boolean completeStatus;
}
