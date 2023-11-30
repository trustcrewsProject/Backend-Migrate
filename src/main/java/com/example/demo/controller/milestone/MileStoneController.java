package com.example.demo.controller.milestone;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.milestone.MilestoneCreateResponseDto;
import com.example.demo.dto.milestone.request.MilestoneCreateRequestDto;
import com.example.demo.service.milestone.MileStoneFacade;
import com.example.demo.service.milestone.MilestoneService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
