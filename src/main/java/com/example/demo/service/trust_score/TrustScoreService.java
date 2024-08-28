package com.example.demo.service.trust_score;

import com.example.demo.dto.trust_score.AddPointDto;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.model.trust_score.TrustScore;

import javax.validation.Valid;

public interface TrustScoreService {
    TrustScore addPoint(Long userId);
    void addPoint(TrustGrade trustGrade, @Valid AddPointDto addPointDto);

    TrustScore findTrustScoreByUserId(Long userId);
}
