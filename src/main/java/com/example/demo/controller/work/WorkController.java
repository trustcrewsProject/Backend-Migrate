package com.example.demo.controller.work;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.work.request.*;
import com.example.demo.dto.work.response.WorkReadResponseDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.work.WorkFacade;
import com.example.demo.service.work.WorkService;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/work")
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;
    private final WorkFacade workFacade;

    @PostMapping("/project/{projectId}/milestone/{milestoneId}")
    public ResponseEntity<ResponseDto<?>> create(
            @AuthenticationPrincipal PrincipalDetails user,
            @PathVariable("projectId") Long projectId,
            @PathVariable("milestoneId") Long milestoneId,
            @Valid @RequestBody WorkCreateRequestDto workCreateRequestDto) {
        workFacade.create(user.getId(), projectId, milestoneId, workCreateRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ResponseDto<?>> getAllByProject(
            @PathVariable("projectId") Long projectId) {
        List<WorkReadResponseDto> result = workFacade.getAllByProject(projectId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}/milestone/{milestoneId}")
    public ResponseEntity<ResponseDto<?>> getAllByMilestone(
            @PathVariable("projectId") Long projectId,
            @PathVariable("milestoneId") Long milestoneId,
            @RequestParam("pageIndex") Optional<Integer> pageIndex,
            @RequestParam("itemCount") Optional<Integer> itemCount) {
        return new ResponseEntity<>(ResponseDto.success("success", workFacade.getAllByMilestone(projectId, milestoneId, pageIndex.orElse(0), itemCount.orElse(6))), HttpStatus.OK);
    }

    @GetMapping("/{workId}")
    public ResponseEntity<ResponseDto<?>> getOne(@PathVariable("workId") Long workId) {
        WorkReadResponseDto result = workFacade.getOne(workId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @PatchMapping("/{workId}")
    public ResponseEntity<ResponseDto<?>> update(
            @AuthenticationPrincipal PrincipalDetails user,
            @PathVariable("workId") Long workId,
            @RequestBody WorkUpdateRequestDto workUpdateRequestDto) {
        workFacade.update(user.getId(), workId, workUpdateRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @DeleteMapping("/{workId}")
    public ResponseEntity<ResponseDto<?>> delete(
            @AuthenticationPrincipal PrincipalDetails user,
            @PathVariable("workId") Long workId) {
        workFacade.deleteWork(user.getId(), workId);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @PatchMapping("/{workId}/content")
    public ResponseEntity<ResponseDto<?>> updateContent(
            @PathVariable("workId") Long workId,
            @RequestBody WorkUpdateContentRequestDto workUpdateContentRequestDto) {
        workFacade.updateContent(workId, workUpdateContentRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @PatchMapping("/{workId}/complete")
    public ResponseEntity<ResponseDto<?>> updateCompleteStatus(
            @PathVariable("workId") Long workId,
            @RequestBody WorkUpdateCompleteStatusRequestDto workUpdateCompleteStatusRequestDto) {
        workFacade.updateCompleteStatus(workId, workUpdateCompleteStatusRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @PatchMapping("/{workId}/assign")
    public ResponseEntity<ResponseDto<?>> updateAssign(
            @PathVariable("workId") Long workId,
            @RequestBody WorkUpdateAssignUserRequestDto workUpdateAssignUserRequestDto) {
        workFacade.updateAssignUser(workId, workUpdateAssignUserRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @PostMapping("/confirm")
    public ResponseEntity<ResponseDto<?>> workConfirm(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestBody WorkConfirmRequestDto workConfirmRequest) {
        workFacade.workConfirm(user.getId(), workConfirmRequest);
        return new ResponseEntity<>(ResponseDto.success("업무 컨펌이 완료되었습니다."), HttpStatus.OK);
    }
}
