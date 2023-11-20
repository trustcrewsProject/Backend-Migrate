package com.example.demo.controller.position;

import com.example.demo.service.position.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;
}
