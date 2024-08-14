package com.example.demo.repository.projectAlert.vote.fwithdraw;

import com.example.demo.dto.projectAlert.vote.fwithdraw.VAlertFWResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VAlertFWithdrawRepositoryCustom {
    List<VAlertFWResponseDto> findVAlertFWList(Long projectId, Pageable pageable);

    Long countVAlertFWList(Long projectId);

}
