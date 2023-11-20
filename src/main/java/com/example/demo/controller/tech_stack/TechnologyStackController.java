package com.example.demo.controller.tech_stack;

import com.example.demo.service.tech_stack.TechnologyStackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TechnologyStackController {

    private final TechnologyStackService technologyStackService;
}
