package com.example.demo.dto.trust_score_type.response;

import com.example.demo.model.trust_score.TrustScoreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
@Builder
public class TrustScoreTypeReadResponseDto {

    private Long trustScoreTypeId;
    private String upTrustScoreTypeName;
    private String trustScoreTypeName;

    private String trustGradeName;

    private Integer score;

    private String gubunCode;

    private String deleteStatus;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    public static TrustScoreTypeReadResponseDto of(TrustScoreType trustScoreType) {
        return TrustScoreTypeReadResponseDto.builder()
                .trustScoreTypeId(trustScoreType.getId())
                .upTrustScoreTypeName(getUpTrustScoreTypeName(trustScoreType.getUpTrustScoreType()))
                .trustScoreTypeName(trustScoreType.getTrustScoreTypeName())
                .trustGradeName(trustScoreType.getTrustGradeName())
                .score(trustScoreType.getScore())
                .gubunCode(trustScoreType.getGubunCode())
                .deleteStatus(trustScoreType.getDeleteStatus())
                .createDate(trustScoreType.getCreateDate())
                .updateDate(trustScoreType.getUpdateDate())
                .build();
    }

    private static String getUpTrustScoreTypeName(TrustScoreType trustScoreType) {
        if (trustScoreType == null) {
            return null;
        }
        return trustScoreType.getTrustScoreTypeName();
    }
}
