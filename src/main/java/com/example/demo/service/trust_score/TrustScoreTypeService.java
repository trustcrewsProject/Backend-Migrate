package com.example.demo.service.trust_score;

import com.example.demo.dto.trust_score_type.response.TrustScoreTypeReadResponseDto;

import java.util.List;

public interface TrustScoreTypeService {
    List<TrustScoreTypeReadResponseDto> getAllAndReturnDto();
}
