package com.example.demo.repository;

import com.example.demo.model.TrustScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrustScoreRepository extends JpaRepository<TrustScore, Long>, TrustScoreRepositoryCustom {
}
