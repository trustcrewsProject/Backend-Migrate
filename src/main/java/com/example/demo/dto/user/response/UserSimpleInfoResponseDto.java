package com.example.demo.dto.user.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSimpleInfoResponseDto {

    private String nickname;

    private String profileImgSrc;

    public static UserSimpleInfoResponseDto of(String nickname, String profileImgSrc) {
        return UserSimpleInfoResponseDto.builder()
                .nickname(nickname)
                .profileImgSrc(profileImgSrc)
                .build();
    }
}
