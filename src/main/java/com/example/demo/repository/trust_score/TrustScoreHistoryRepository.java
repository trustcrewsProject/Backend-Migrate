package com.example.demo.repository.trust_score;

import com.example.demo.model.TrustScoreHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrustScoreHistoryRepository extends JpaRepository<TrustScoreHistory, Long>, TrustScoreHistoryRepositoryCustom {
}
