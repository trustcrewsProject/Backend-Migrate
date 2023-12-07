package com.example.demo.dto.user.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginSuccessResponseDto {

    private Long userId;
    private String email;

    public static UserLoginSuccessResponseDto of(
            Long userId, String email) {
        return UserLoginSuccessResponseDto.builder()
                .userId(userId)
                .email(email)
                .build();
    }
}
