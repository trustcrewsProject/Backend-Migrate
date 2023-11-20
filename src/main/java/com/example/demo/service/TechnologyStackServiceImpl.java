package com.example.demo.service;

import com.example.demo.repository.tech_stack.TechnologyStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechnologyStackServiceImpl implements TechnologyStackService {

    private final TechnologyStackRepository technologyStackRepository;
}
