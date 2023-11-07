package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TrustGradeRequestDto {
    private Long id;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    public String name;
    @NotBlank(message = "신뢰 점수는 필수 입력 값입니다.")
    public int score;

    public TrustGradeRequestDto(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
