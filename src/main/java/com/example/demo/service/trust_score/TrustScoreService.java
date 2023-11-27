package com.example.demo.service.trust_score;

import com.example.demo.dto.trust_score.request.TrustScoreUpdateRequestDto;
import com.example.demo.dto.trust_score.response.TrustScoreUpdateResponseDto;
import org.springframework.stereotype.Service;

@Service
public interface TrustScoreService {
    TrustScoreUpdateResponseDto updateTrustScore(TrustScoreUpdateRequestDto requestDto);
}
