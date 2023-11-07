package com.example.demo.repository;

import com.example.demo.model.TrustGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrustGradeRepository extends JpaRepository<TrustGrade,Long> {
    boolean existsByName(String name);
}
