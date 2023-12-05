package com.example.demo.service.trust_score;

import com.example.demo.repository.trust_score.TrustScoreHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrustScoreTypeServiceImpl implements TrustScoreTypeService {
    private final TrustScoreHistoryRepository trustScoreHistoryRepository;
}
