package com.example.demo.controller.technology_stack;

import com.example.demo.service.technology_stack.TechnologyStackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TechnologyStackController {

    private final TechnologyStackService technologyStackService;
}
