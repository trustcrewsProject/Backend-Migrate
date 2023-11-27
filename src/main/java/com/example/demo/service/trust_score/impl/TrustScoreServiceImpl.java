package com.example.demo.service.trust_score.impl;

import com.example.demo.dto.trust_score.request.TrustScoreUpdateRequestDto;
import com.example.demo.dto.trust_score.response.TrustScoreUpdateResponseDto;
import com.example.demo.model.trust_score.TrustScoreHistory;
import com.example.demo.repository.trust_score.TrustScoreHistoryRepository;
import com.example.demo.repository.trust_score.TrustScoreRepository;
import com.example.demo.repository.trust_score.TrustScoreTypeRepository;
import com.example.demo.service.trust_score.TrustScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TrustScoreServiceImpl implements TrustScoreService {
    private final TrustScoreRepository trustScoreRepository;
    private final TrustScoreHistoryRepository trustScoreHistoryRepository;
    private final TrustScoreTypeRepository trustScoreTypeRepository;
    @Override
    @Transactional
    public TrustScoreUpdateResponseDto updateTrustScore(TrustScoreUpdateRequestDto requestDto) {
        // 신뢰점수 조회
        int score = getScore(requestDto);
        // 신뢰점수내역 저장
        createAndSaveHistory(requestDto, score);
        // 마일리지 누적점수 업데이트
        trustScoreRepository.updateScore(requestDto.getUserId(), score);
        return TrustScoreUpdateResponseDto.builder()
                .userId(requestDto.getUserId())
                .score(score)
                .build();
    }
    /**
     * 신뢰점수이력 생성 및 조회
     * @param requestDto, score
     * @return TrustScoreHistory
     */
    private TrustScoreHistory createAndSaveHistory(TrustScoreUpdateRequestDto requestDto, int score) {
        TrustScoreHistory history = TrustScoreHistory.builder()
                .userId(requestDto.getUserId())
                .trustScoreTypeId(requestDto.getScoreTypeId())
                .projectId(requestDto.getProjectId())
                .milestoneId(requestDto.getMilestoneId())
                .workId(requestDto.getWorkId())
                .score(score)
                .createDate(new Date())
                .build();
        return trustScoreHistoryRepository.save(history);
    }
    /**
     * 신뢰점수 조회
     * @param requestDto
     * @return int
     */
    private int getScore(TrustScoreUpdateRequestDto requestDto) {
        int score = 0;
        if (requestDto.getProjectId() == null) {
            score = trustScoreTypeRepository.getScore(requestDto.getScoreTypeId());
        } else {
            score = trustScoreTypeRepository.getScoreByProject(requestDto.getProjectId(), requestDto.getScoreTypeId());
        }
        return score;
    }
}