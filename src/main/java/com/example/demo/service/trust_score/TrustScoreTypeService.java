package com.example.demo.service.trust_score;

import com.example.demo.dto.trust_score_type.TrustScoreTypeSearchCriteria;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeReadResponseDto;
import com.example.demo.model.trust_score.TrustScoreType;

import java.util.List;

public interface TrustScoreTypeService {
    List<TrustScoreTypeReadResponseDto> getAllAndReturnDto();
    List<TrustScoreTypeReadResponseDto> getSearchResults(
            TrustScoreTypeSearchCriteria criteria);
}
