package com.example.demo.repository.projectVote.fwithdraw.history;

import com.example.demo.model.projectVote.fwithdraw.history.VoteFWithdrawHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VFWithdrawHistoryRepository extends JpaRepository<VoteFWithdrawHistory, Long>, VFWithdrawHistoryRepositoryCustom {
}
