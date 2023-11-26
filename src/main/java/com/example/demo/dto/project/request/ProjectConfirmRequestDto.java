package com.example.demo.dto.project.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class ProjectConfirmRequestDto {
    @NotNull(message = "포지션은 필수 입력 값입니다.")
    private Long positionId;
}
