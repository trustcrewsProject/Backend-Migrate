package com.example.demo.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@AllArgsConstructor
public class UserUpdateRequestDto {

    @NotBlank(message = "변경할 이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String modifiedEmail;
}
