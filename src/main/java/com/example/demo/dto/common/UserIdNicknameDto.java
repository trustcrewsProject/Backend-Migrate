package com.example.demo.dto.common;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserIdNicknameDto {
    private Long userId;
    private String userNickname;

    @Builder
    public UserIdNicknameDto(Long userId, String userNickname){
        this.userId = userId;
        this.userNickname = userNickname;
    }
}
