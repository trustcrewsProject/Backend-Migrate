package com.example.demo.dto.user;

import com.example.demo.model.Position;
import com.example.demo.model.TrustGrade;
import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserResponseDto {
    private long userId;
    private String email;
    private String nickName;
    private String profileImgSrc;
    private String intro;
    private int trustScore;
    private String role;
    private TrustGrade trustGrade;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Position position;
}
