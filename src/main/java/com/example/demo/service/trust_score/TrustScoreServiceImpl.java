package com.example.demo.service.trust_score;

import com.example.demo.repository.trust_score.TrustScoreRepository;
import com.example.demo.service.trust_score.TrustScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrustScoreServiceImpl implements TrustScoreService {
    private final TrustScoreRepository trustScoreRepository;
}
