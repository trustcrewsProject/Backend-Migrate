package com.example.demo.dto.trust_grade.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TrustGradeInfoResponseDto {

    private Long trustGradeId;

    private String trustGradeName;

    private int trustGradeScore;

    public static TrustGradeInfoResponseDto of (Long trustGradeId, String trustGradeName, int trustGradeScore) {
        return TrustGradeInfoResponseDto.builder()
                .trustGradeId(trustGradeId)
                .trustGradeName(trustGradeName)
                .trustGradeScore(trustGradeScore)
                .build();
    }
}
