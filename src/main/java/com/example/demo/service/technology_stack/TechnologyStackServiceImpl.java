package com.example.demo.service.technology_stack;

import com.example.demo.repository.technology_stack.TechnologyStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechnologyStackServiceImpl implements TechnologyStackService {

    private final TechnologyStackRepository technologyStackRepository;
}
