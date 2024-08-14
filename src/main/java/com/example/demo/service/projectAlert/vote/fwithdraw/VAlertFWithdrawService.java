package com.example.demo.service.projectAlert.vote.fwithdraw;

import com.example.demo.dto.projectAlert.vote.fwithdraw.VAlertFWResponseDto;
import com.example.demo.model.projectVote.fwithdraw.VoteFWithdraw;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface VAlertFWithdrawService {
    void toVAlertFWithdrawEntity(Long project_id, VoteFWithdraw voteFWithdraw, String alertContents);
    List<VAlertFWResponseDto> findVAlertFWList(Long projectId, Pageable pageable);
    Long countVAlertFWList(Long projectId);

}
