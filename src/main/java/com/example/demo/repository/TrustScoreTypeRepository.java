package com.example.demo.repository;

import com.example.demo.model.TrustScoreType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrustScoreTypeRepository extends JpaRepository<TrustScoreType, Long>, TrustScoreTypeRepositoryCustom
{
}
