package com.example.demo.repository.milestone;

import com.example.demo.model.milestone.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MileStoneRepository extends JpaRepository<Milestone, Long> {
    @Override
    Optional<Milestone> findById(Long id);
}
