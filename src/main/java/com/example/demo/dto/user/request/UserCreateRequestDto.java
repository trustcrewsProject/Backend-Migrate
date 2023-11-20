package com.example.demo.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserCreateRequestDto {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식을 확인해주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{6,12}$")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,10}$")
    private String nickname;

    @NotNull(message = "직무는 필수 선택 값입니다.")
    private Long positionId;

    @NotEmpty(message = "관심스택은 필수 선택 값입니다.")
    private List<Long> techStackIds;

    private String intro;
}
