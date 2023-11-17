package com.example.demo.repository;

import com.example.demo.model.UserProjectHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProjectHistoryRepository extends JpaRepository<UserProjectHistory, Long> {
}
