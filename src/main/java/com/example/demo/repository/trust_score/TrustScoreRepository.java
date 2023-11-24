package com.example.demo.repository.trust_score;

import com.example.demo.model.trust_score.TrustScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrustScoreRepository
        extends JpaRepository<TrustScore, Long>, TrustScoreRepositoryCustom {}
