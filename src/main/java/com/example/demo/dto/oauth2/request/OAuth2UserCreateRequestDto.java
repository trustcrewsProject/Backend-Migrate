package com.example.demo.dto.oauth2.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@AllArgsConstructor
public class OAuth2UserCreateRequestDto {

    @NotBlank(message = "OAuth 제공자 정보는 필수 요청 값입니다.")
    private String oAuthProvider;

    @NotBlank(message = "OAuth 제공자 고유 번호는 필수 요청 값입니다.")
    private String oAuthProviderId;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,10}$", message = "닉네임은 영문과 숫자를 조합하여 6자~10자 사이로 설정해주세요.")
    private String nickname;

    @NotNull(message = "직무는 필수 선택 값입니다.")
    private Long positionId;

    @NotEmpty(message = "관심스택은 필수 선택 값입니다.")
    private List<Long> techStackIds;

    private String intro;
}
