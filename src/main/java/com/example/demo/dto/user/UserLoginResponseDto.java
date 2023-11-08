package com.example.demo.dto.user;

import com.example.demo.model.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginResponseDto {

    private Long userId;

    private String email;

    public static UserLoginResponseDto from (User user) {
        return UserLoginResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .build();
    }
}
