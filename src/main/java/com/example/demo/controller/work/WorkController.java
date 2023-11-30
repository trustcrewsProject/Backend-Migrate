package com.example.demo.controller.work;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.work.request.WorkCreateRequestDto;
import com.example.demo.service.work.WorkFacade;
import com.example.demo.service.work.WorkService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;
    private final WorkFacade workFacade;

    @PostMapping("/api/project/{projectId}/milestone/{milestoneId}/work")
    public ResponseEntity<ResponseDto<?>> create(
            @PathVariable("projectId") Long projectId,
            @PathVariable("milestoneId") Long milestoneId,
            WorkCreateRequestDto workCreateRequestDto) {
        workFacade.create(projectId, milestoneId, workCreateRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }
}
