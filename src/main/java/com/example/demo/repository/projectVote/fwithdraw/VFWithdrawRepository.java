package com.example.demo.repository.projectVote.fwithdraw;

import com.example.demo.model.projectVote.fwithdraw.VoteFWithdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VFWithdrawRepository extends JpaRepository<VoteFWithdraw, Long>, VFWithdrawRepositoryCustom {
}
