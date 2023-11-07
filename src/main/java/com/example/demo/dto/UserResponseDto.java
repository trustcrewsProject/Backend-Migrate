package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private User user;

    public UserResponseDto(User user) {
        this.user = user;
    }
}
