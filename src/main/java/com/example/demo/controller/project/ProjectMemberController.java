package com.example.demo.controller.project;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.project.request.ProjectWithdrawConfirmRequestDto;
import com.example.demo.dto.projectmember.response.ProjectMemberReadCrewDetailResponseDto;
import com.example.demo.dto.projectmember.response.ProjectMemberReadTotalProjectCrewsResponseDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.project.ProjectMemberFacade;
import com.example.demo.service.project.ProjectMemberService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/projectmember")
@AllArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;
    private final ProjectMemberFacade projectMemberFacade;

    @PostMapping("/{projectMemberId}/withdraw")
    public ResponseEntity<ResponseDto<?>> withdraw(
            @AuthenticationPrincipal PrincipalDetails user,
            @PathVariable("projectMemberId") Long projectMemberId) {
        projectMemberFacade.sendWithdrawAlert(user.getId(), projectMemberId);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @PostMapping("/withdraw/confirm")
    public ResponseEntity<ResponseDto<?>> withdrawConfirm(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestBody ProjectWithdrawConfirmRequestDto withdrawConfirmRequest) {
        projectMemberFacade.withdrawConfirm(user.getId(), withdrawConfirmRequest);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @PostMapping("/{projectMemberId}/withdrawl/force")
    public ResponseEntity<ResponseDto<?>> withdrawlForce(
            @PathVariable("projectMemberId") Long projectMemberId) {
        projectMemberService.withdrawlForce(projectMemberId);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @GetMapping("/{projectMemberId}")
    public ResponseEntity<ResponseDto<?>> getDetail(
            @PathVariable("projectMemberId") Long projectMemberId) {
        ProjectMemberReadCrewDetailResponseDto result =
                projectMemberFacade.getCrewDetail(projectMemberId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ResponseDto<?>> getCrewDetailsByProject(
            @PathVariable("projectId") Long projectId) {
        ProjectMemberReadTotalProjectCrewsResponseDto result =
                projectMemberFacade.getCrewsByProject(projectId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    // 프로젝트 멤버 업무 이력 조회
    @GetMapping("/{projectMemberId}/works")
    public ResponseEntity<ResponseDto<?>> getCrewWorksWithTrustScoreHistory(
            @PathVariable("projectMemberId") Long projectMemberId,
            @RequestParam("pageIndex") Optional<Integer> pageIndex,
            @RequestParam("itemCount") Optional<Integer> itemCount) {
        return new ResponseEntity<>(ResponseDto.success("success", projectMemberFacade.getCrewWorksWithTrustScoreHistory(projectMemberId, pageIndex.orElse(0), itemCount.orElse(5))), HttpStatus.OK);
    }

}
