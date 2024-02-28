package com.example.demo.controller.alert;

import com.example.demo.dto.alert.AlertCreateRequestDto;
import com.example.demo.dto.alert.response.AlertInfoResponseDto;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.alert.AlertFacade;
import com.example.demo.service.alert.AlertService;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;

@RestController
@RequestMapping("/api/alert")
@RequiredArgsConstructor
public class AlertController {

    private final AlertFacade alertFacade;
    private final AlertService alertService;

    @PostMapping()
    public ResponseEntity<ResponseDto<?>> send(
            @RequestBody AlertCreateRequestDto alertCreateRequestDto) {
        alertFacade.send(alertCreateRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ResponseDto<?>> getAllByProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam("pageIndex") Optional<Integer> pageIndex,
            @RequestParam("itemCount") Optional<Integer> itemCount) {
        return new ResponseEntity<>(ResponseDto.success("success",
                alertFacade.getAllByProject(projectId, pageIndex.orElse(0), itemCount.orElse(6))), HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}/recruits")
    public ResponseEntity<ResponseDto<?>> getRecruitsByProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam("pageIndex") Optional<Integer> pageIndex,
            @RequestParam("itemCount") Optional<Integer> itemCount) {
        return new ResponseEntity<>(ResponseDto.success("success",
                alertFacade.getRecruitsByProject(projectId, pageIndex.orElse(0), itemCount.orElse(6))), HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}/works")
    public ResponseEntity<ResponseDto<?>> getWorksByProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam("pageIndex") Optional<Integer> pageIndex,
            @RequestParam("itemCount") Optional<Integer> itemCount) {
        return new ResponseEntity<>(ResponseDto.success("success",
                alertFacade.getWorksByProject(projectId, pageIndex.orElse(0), itemCount.orElse(6))), HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}/crews")
    public ResponseEntity<ResponseDto<?>> getCrewsByProject(
            @PathVariable("projectId") Long projectId,
            @RequestParam("pageIndex") Optional<Integer> pageIndex,
            @RequestParam("itemCount") Optional<Integer> itemCount) {
        return new ResponseEntity<>(ResponseDto.success("success",
                alertFacade.getCrewsByProject(projectId, pageIndex.orElse(0), itemCount.orElse(6))), HttpStatus.OK);
    }

    @PatchMapping("/{alertId}/status")
    public ResponseEntity<ResponseDto<?>> updateAlertStatus(@PathVariable Long alertId) {
        alertService.updateAlertStatus(alertId);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }

    @GetMapping("/supported-projects")
    public ResponseEntity<ResponseDto<?>> getSupportedProjects(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestParam Optional<Integer> pageIndex,
            @RequestParam Optional<Integer> itemCount) {
        return new ResponseEntity<>(
                ResponseDto.success("사용자가 지원한 프로젝트 알림목록 조회가 완료되었습니다.",
                        alertFacade.getSupportedProjects(user.getId(), pageIndex.orElse(0), itemCount.orElse(6))), HttpStatus.OK);
    }
}
