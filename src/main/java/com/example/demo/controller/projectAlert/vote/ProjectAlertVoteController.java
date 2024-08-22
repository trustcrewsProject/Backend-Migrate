package com.example.demo.controller.projectAlert.vote;

import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.projectAlert.vote.fwithdraw.VAlertFWCreateRequestDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.projectAlert.vote.fwithdraw.VAlertFWithdrawFacade;
import com.example.demo.service.projectAlert.vote.recruit.VAlertFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/projectAlert/vote")
@RequiredArgsConstructor
public class ProjectAlertVoteController {

    public final VAlertFacade vAlertFacade;
    public final VAlertFWithdrawFacade vAlertFWithdrawFacade;


    @GetMapping("/recruit")
    public ResponseEntity<ResponseDto<?>> getProjectAlertRecruitList(
            @RequestParam("projectId") Long projectId,
            @RequestParam("pageIndex") Optional<Integer> pageIndex,
            @RequestParam("itemCount") Optional<Integer> itemCount
    ) {
        return new ResponseEntity<>(ResponseDto.success("success",
                vAlertFacade.getProjectAlertRecruitList(
                        projectId,
                        pageIndex.orElse(0),
                        itemCount.orElse(5)
                )),
                HttpStatus.OK
        );
    }

    @GetMapping("/recruit/detail")
    public ResponseEntity<ResponseDto<?>> getProjectAlertRecruitDetail(
            @RequestParam("alertId") Long alertId,
            @RequestParam("applyId") Long applyId,
            @RequestParam("voteId") Long voteId
    ) {
        return new ResponseEntity<>(ResponseDto.success("success",
                vAlertFacade.getProjectAlertRecruitDetail(alertId, applyId, voteId)),
                HttpStatus.OK
        );
    }

    @GetMapping("/fwithdraw")
    public ResponseEntity<ResponseDto<?>> getProjectAlertFWList(
            @RequestParam("projectId") Long projectId,
            @RequestParam("pageIndex") Optional<Integer> pageIndex,
            @RequestParam("itemCount") Optional<Integer> itemCount
    ) {

        return new ResponseEntity<>(ResponseDto.success("success",
                vAlertFWithdrawFacade.getVAlertFWithdrawList(
                        projectId,
                        pageIndex.orElse(0),
                        itemCount.orElse(5)
                )),
                HttpStatus.OK
        );

    }

    @GetMapping("/fwithdraw/detail")
    public ResponseEntity<ResponseDto<?>> getProjectAlertFWDetail(
            @RequestParam("voteId") Long voteId,
            @RequestParam("fwMemberId") Long fwMemberId
    ) {
        return new ResponseEntity<>(ResponseDto.success("success",
                vAlertFWithdrawFacade.getVAlertFWithdrawDetail(voteId, fwMemberId)), HttpStatus.OK);
    }

    @PostMapping("/fwithdraw/create")
    public ResponseEntity<ResponseDto<?>> createProjectAlertFW(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestBody VAlertFWCreateRequestDto requestDto
    ) {
        vAlertFWithdrawFacade.createFWithdrawAlert(user.getId(), requestDto);
        return new ResponseEntity<>(ResponseDto.success("success", null), HttpStatus.OK);
    }

}
