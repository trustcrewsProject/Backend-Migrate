package com.example.demo.repository.projectVote.recruit;

import com.example.demo.model.projectVote.recruit.VoteRecruit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRecruitRepository extends JpaRepository<VoteRecruit, Long>, VoteRecruitRepositoryCustom {
}
