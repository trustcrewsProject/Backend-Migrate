package com.example.demo.repository.projectVote.history;

import com.example.demo.model.projectVote.recruit.history.VoteRecruitHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRecruitHistoryRepository extends JpaRepository<VoteRecruitHistory, Long>, VoteRecruitHistoryRepositoryCustom {
}
