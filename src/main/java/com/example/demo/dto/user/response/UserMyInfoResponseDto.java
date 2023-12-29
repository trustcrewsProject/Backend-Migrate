package com.example.demo.dto.user.response;

import com.example.demo.dto.position.response.PositionInfoResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import com.example.demo.global.util.LocalDateTimeFormatSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserMyInfoResponseDto {

    private Long userId;

    private String email;

    private String nickname;

    private String profileImgSrc;

    private String intro;

    private int trustScore;

    private TrustGradeInfoResponseDto trustGrade;

    private PositionInfoResponseDto position;

    private List<TechnologyStackInfoResponseDto> techStacks;

    private long projectHistoryTotalCount;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime createDate;

    @JsonSerialize(using = LocalDateTimeFormatSerializer.class)
    private LocalDateTime updateDate;

    public static UserMyInfoResponseDto of(
            Long userId,
            String email,
            String nickname,
            String profileImgSrc,
            String intro,
            int trustScore,
            TrustGradeInfoResponseDto trustGrade,
            PositionInfoResponseDto position,
            List<TechnologyStackInfoResponseDto> techStacks,
            long projectHistoryTotalCount,
            LocalDateTime createDate,
            LocalDateTime updateDate) {
        return UserMyInfoResponseDto.builder()
                .userId(userId)
                .email(email)
                .nickname(nickname)
                .profileImgSrc(profileImgSrc)
                .intro(intro)
                .trustScore(trustScore)
                .trustGrade(trustGrade)
                .position(position)
                .techStacks(techStacks)
                .projectHistoryTotalCount(projectHistoryTotalCount)
                .createDate(createDate)
                .updateDate(updateDate)
                .build();
    }
}
