package com.example.demo.service;

import com.example.demo.repository.trust_score.TrustScoreHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrustScoreHistoryServiceImpl implements TrustScoreHistoryService {
    private final TrustScoreHistoryRepository trustScoreHistoryRepository;
}
