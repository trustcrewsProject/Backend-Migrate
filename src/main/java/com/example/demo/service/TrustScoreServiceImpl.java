package com.example.demo.service;

import com.example.demo.repository.TrustScoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrustScoreServiceImpl implements TrustScoreService{
    private final TrustScoreRepository trustScoreRepository;
}
