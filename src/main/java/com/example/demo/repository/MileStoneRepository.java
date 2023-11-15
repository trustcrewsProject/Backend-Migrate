package com.example.demo.repository;

import com.example.demo.model.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MileStoneRepository extends JpaRepository<Milestone, Long> {
}
