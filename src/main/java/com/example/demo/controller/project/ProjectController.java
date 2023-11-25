package com.example.demo.controller.project;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.project.response.ProjectMeResponseDto;
import com.example.demo.dto.project.response.ProjectSpecificDetailResponseDto;
import com.example.demo.service.project.ProjectService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@AllArgsConstructor
public class ProjectController {

    public final ProjectService projectService;

    @GetMapping("/me")
    public ResponseEntity<ResponseDto<?>> getMyProjects() {
        try{
            List<ProjectMeResponseDto> result = projectService.getMyProjects();
            return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<>(ResponseDto.fail(ex.getMessage()), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ResponseDto<?>> getDetail(@PathVariable("projectId") Long projectId) {
        ProjectSpecificDetailResponseDto result = projectService.getDetail(projectId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }
}
