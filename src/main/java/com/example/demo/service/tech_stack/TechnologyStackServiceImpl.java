package com.example.demo.service.tech_stack;

import com.example.demo.repository.tech_stack.TechnologyStackRepository;
import com.example.demo.service.tech_stack.TechnologyStackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechnologyStackServiceImpl implements TechnologyStackService {

    private final TechnologyStackRepository technologyStackRepository;
}
