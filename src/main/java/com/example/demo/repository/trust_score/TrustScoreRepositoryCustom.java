package com.example.demo.repository.trust_score;

public interface TrustScoreRepositoryCustom {
    void updateTrustGradeBatch();
    void updateUserTrustGrade(Long userId);
}
