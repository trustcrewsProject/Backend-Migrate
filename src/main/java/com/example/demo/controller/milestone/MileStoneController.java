package com.example.demo.controller;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.milestone.MilestoneCreateResponseDto;
import com.example.demo.dto.milestone.Request.MileStoneUpdateRequestDto;
import com.example.demo.dto.milestone.Request.MilestoneCreateRequestDto;
import com.example.demo.dto.milestone.Request.MilestoneUpdateContentRequestDto;
import com.example.demo.dto.milestone.Request.MilestoneUpdateDateRequestDto;
import com.example.demo.dto.milestone.Response.MilestoneCreateResponseDto;
import com.example.demo.dto.milestone.Response.MilestoneReadResponseDto;
import com.example.demo.dto.milestone.request.MilestoneCreateRequestDto;
import com.example.demo.dto.milestone.response.MilestoneReadResponseDto;
import com.example.demo.global.exception.customexception.ProjectCustomException;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.service.MilestoneService;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.service.milestone.MileStoneFacade;
import com.example.demo.service.milestone.MilestoneService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class MileStoneController {

    private final MilestoneService milestoneService;
    private MileStoneFacade mileStoneFacade;

    @PostMapping("/api/project/{projectId}/milestone")
    public ResponseEntity<ResponseDto<?>> create(
            @PathVariable("projectId") Long projectId,
            @RequestBody MilestoneCreateRequestDto milestoneCreateRequestDto) {
        MilestoneCreateResponseDto result = mileStoneFacade.create(projectId, milestoneCreateRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }
}
