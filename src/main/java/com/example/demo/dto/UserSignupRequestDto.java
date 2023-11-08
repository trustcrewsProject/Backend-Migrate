package com.example.demo.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class UserSignupRequestDto {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(
            regexp = "^[a-zA-Z]{6,}[^0-9a-zA-Zㄱ-ㅎ가-힣]$",
            message = "비밀번호는 6자 이상의 영문으로만 조합하고 마지막에는 특수문자 1개를 포함해야 합니다.")
    private String password;
}
