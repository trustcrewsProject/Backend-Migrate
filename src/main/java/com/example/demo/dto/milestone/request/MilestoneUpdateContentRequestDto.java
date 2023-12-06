package com.example.demo.dto.milestone.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MilestoneUpdateContentRequestDto {

    @NotBlank(message = "내용은 필수 사항입니다.")
    private String content;
}
