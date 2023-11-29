package com.example.demo.dto.user.response;

import com.example.demo.constant.Role;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.model.user.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserCrewDetailResponseDto {
    private Long userId;
    private String email;
    private String nickname;
    private String profileImgSrc;
    private PositionResponseDto position;
    private TrustGradeResponseDto trustGrade;
    private int trustScore;
    private Role role;
    private String intro;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static UserCrewDetailResponseDto of(User user, PositionResponseDto position, TrustGradeResponseDto trustGrade) {
        return UserCrewDetailResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getEmail())
                .profileImgSrc(user.getProfileImgSrc())
                .position(position)
                .trustGrade(trustGrade)
                .trustScore(user.getTrustScore())
                .role(user.getRole())
                .intro(user.getIntro())
                .createDate(user.getCreateDate())
                .updateDate(user.getUpdateDate())
                .build();
    }
}
