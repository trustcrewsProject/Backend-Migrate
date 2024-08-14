package com.example.demo.service.projectAlert.vote.fwithdraw;

import com.example.demo.dto.projectAlert.vote.fwithdraw.VAlertFWResponseDto;
import com.example.demo.model.project.alert.vote.VAlertFWithdraw;
import com.example.demo.model.projectVote.fwithdraw.VoteFWithdraw;
import com.example.demo.repository.projectAlert.vote.fwithdraw.VAlertFWithdrawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VAlertFWithdrawServiceImpl implements VAlertFWithdrawService {

    private final VAlertFWithdrawRepository vAlertFWithdrawRepository;
    @Override
    public void toVAlertFWithdrawEntity(Long project_id, VoteFWithdraw voteFWithdraw, String alertContents) {
        VAlertFWithdraw vAlertFWithdraw = VAlertFWithdraw.builder()
                .project_id(project_id)
                .voteFWithdraw(voteFWithdraw)
                .alertContents(alertContents)
                .build();
        vAlertFWithdrawRepository.save(vAlertFWithdraw);
    }

    @Override
    public List<VAlertFWResponseDto> findVAlertFWList(Long projectId, Pageable pageable) {
        return vAlertFWithdrawRepository.findVAlertFWList(projectId, pageable);
    }

    @Override
    public Long countVAlertFWList(Long projectId) {
        return vAlertFWithdrawRepository.countVAlertFWList(projectId);
    }

}
