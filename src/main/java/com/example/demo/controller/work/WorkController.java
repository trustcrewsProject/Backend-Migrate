package com.example.demo.controller.work;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.work.request.WorkCompleteRequestDto;
import com.example.demo.dto.work.request.WorkCreateRequestDto;
import com.example.demo.dto.work.request.WorkUpdateRequestDto;
import com.example.demo.dto.work.response.WorkReadResponseDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.work.WorkFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/project/work")
@RequiredArgsConstructor
public class WorkController {

    private final WorkFacade workFacade;

    @PostMapping("")
    public ResponseEntity<ResponseDto<?>> create(
            @AuthenticationPrincipal PrincipalDetails user,
            @Valid @RequestBody WorkCreateRequestDto workCreateRequestDto) {
        workFacade.create(user.getId(), workCreateRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto<?>> getAllByMilestone(
            @RequestParam("projectId") Long projectId,
            @RequestParam("milestoneId") Long milestoneId,
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

    @PostMapping("/complete")
    public ResponseEntity<ResponseDto<?>> workComplete(
            @RequestBody WorkCompleteRequestDto requestDto) {
        workFacade.workComplete(requestDto);
        return new ResponseEntity<>(ResponseDto.success("업무를 완료했습니다."), HttpStatus.OK);
    }
}
