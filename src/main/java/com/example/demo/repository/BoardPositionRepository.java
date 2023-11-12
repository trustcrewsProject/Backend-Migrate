package com.example.demo.repository;

import com.example.demo.model.BoardPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardPositionRepository extends JpaRepository<BoardPosition, Long> {
}
