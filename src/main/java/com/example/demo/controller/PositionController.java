package com.example.demo.controller;

import com.example.demo.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;
}
