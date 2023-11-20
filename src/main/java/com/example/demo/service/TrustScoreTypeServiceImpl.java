package com.example.demo.service;

import com.example.demo.repository.TrustScoreHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrustScoreTypeServiceImpl implements TrustScoreTypeService {
    private final TrustScoreHistoryRepository trustScoreHistoryRepository;
}
