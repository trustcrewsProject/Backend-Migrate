package com.example.demo.service.trust_score;

import com.example.demo.dto.trust_score.AddPointDto;
import com.example.demo.dto.trust_score.response.TrustScoreUpdateResponseDto;

import javax.validation.Valid;

public interface TrustScoreService {
    TrustScoreUpdateResponseDto addPoint(@Valid AddPointDto addPointDto);
}
