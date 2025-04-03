package com.example.demo.dto.user.response;

import com.example.demo.constant.Role;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.example.demo.model.user.User;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

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
    private List<TechnologyStackInfoResponseDto> technologyStacks;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime createDate;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime updateDate;

    public static UserCrewDetailResponseDto of(
            User user,
            String profileImgSrc,
            int trustScore,
            PositionResponseDto position,
            TrustGradeResponseDto trustGrade,
            List<TechnologyStackInfoResponseDto> technologyStacks) {
        return UserCrewDetailResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImgSrc(profileImgSrc)
                .position(position)
                .trustGrade(trustGrade)
                .trustScore(trustScore)
                .role(user.getRole())
                .intro(user.getIntro())
                .technologyStacks(technologyStacks)
                .createDate(user.getCreateDate())
                .updateDate(user.getUpdateDate())
                .build();
    }
}
