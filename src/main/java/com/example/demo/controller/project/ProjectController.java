package com.example.demo.controller.project;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.project.response.ProjectSpecificDetailResponseDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.project.ProjectFacade;
import com.example.demo.service.project.ProjectMemberService;
import com.example.demo.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectController {

    public final ProjectService projectService;
    public final ProjectFacade projectFacade;
    public final ProjectMemberService projectMemberService;

    @GetMapping("/me/participating")
    public ResponseEntity<ResponseDto<?>> getMyProjectsParticipates(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestParam("pageIndex") Optional<Integer> pageIndex,
            @RequestParam("itemCount") Optional<Integer> itemCount) {
        return new ResponseEntity<>(ResponseDto.success("success", projectFacade.getMyParticipatingProjects(user.getId(), pageIndex.orElse(0), itemCount.orElse(6))), HttpStatus.OK);
    }

    @GetMapping("/{projectId}/{userId}")
    public ResponseEntity<ResponseDto<?>> getDetail(
            @PathVariable("projectId") Long projectId,
            @PathVariable("userId") Long userId) {
        ProjectSpecificDetailResponseDto result = projectFacade.getDetail(userId, projectId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @PostMapping("/{projectId}/end")
    public ResponseEntity<ResponseDto<?>> end(
            @PathVariable("projectId") Long projectId,
            @AuthenticationPrincipal PrincipalDetails user) {
        projectFacade.endProject(user.getId(), projectId);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

}
