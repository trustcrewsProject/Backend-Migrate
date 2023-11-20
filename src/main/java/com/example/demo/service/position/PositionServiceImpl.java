package com.example.demo.service.position;

import com.example.demo.repository.position.PositionRepository;
import com.example.demo.service.position.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;
}
