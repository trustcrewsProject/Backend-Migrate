package com.example.demo.dto.trust_grade.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TrustGradeInfoResponseDto {

    private Long trustGradeId;

    private String trustGradeName;

    public static TrustGradeInfoResponseDto of(
            Long trustGradeId, String trustGradeName) {
        return TrustGradeInfoResponseDto.builder()
                .trustGradeId(trustGradeId)
                .trustGradeName(trustGradeName)
                .build();
    }
}
