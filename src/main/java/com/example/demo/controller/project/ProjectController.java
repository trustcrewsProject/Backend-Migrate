package com.example.demo.controller;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.project.Request.ProjectConfirmRequestDto;
import com.example.demo.dto.project.Request.ProjectParticipateRequestDto;
import com.example.demo.dto.project.Response.ProjectMeResponseDto;
import com.example.demo.dto.project.Response.ProjectSpecificDetailResponseDto;
import com.example.demo.dto.project.response.ProjectMeResponseDto;
import com.example.demo.service.ProjectService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/project")
@AllArgsConstructor
public class ProjectController {

    public final ProjectService projectService;

    @GetMapping("/me")
    public ResponseEntity<ResponseDto<?>> getMyProjects() {
        List<ProjectMeResponseDto> result = projectService.getMyProjects();
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }
}
