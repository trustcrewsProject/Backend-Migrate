package com.example.demo.dto.user.response;

import com.example.demo.dto.position.response.PositionInfoResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserInfoResponseDto {

    private Long userId;

    private String email;

    private String nickname;

    private int trustScore;

    private TrustGradeInfoResponseDto trustGrade;

    private PositionInfoResponseDto position;

    private List<TechnologyStackInfoResponseDto> techStacks;

    private String createDate;

    private String updateDate;

    public static UserInfoResponseDto of(Long userId, String email, String nickname, int trustScore, TrustGradeInfoResponseDto trustGrade,
                                          PositionInfoResponseDto position, List<TechnologyStackInfoResponseDto> techStacks, String createDate, String updateDate) {
        return UserInfoResponseDto.builder()
                .userId(userId)
                .email(email)
                .nickname(nickname)
                .trustScore(trustScore)
                .trustGrade(trustGrade)
                .position(position)
                .techStacks(techStacks)
                .createDate(createDate)
                .updateDate(updateDate)
                .build();
    }
}
