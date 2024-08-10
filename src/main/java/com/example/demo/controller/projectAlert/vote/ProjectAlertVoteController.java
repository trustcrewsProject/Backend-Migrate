package com.example.demo.controller.projectAlert.vote;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.service.projectAlert.vote.recruit.VAlertFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/projectAlert/vote")
@RequiredArgsConstructor
public class ProjectAlertVoteController {

    public final VAlertFacade vAlertFacade;

    @GetMapping("/vote")
    public ResponseEntity<ResponseDto<?>> getProjectAlertRecruitList(
            @RequestParam("projectId") Long projectId,
            @RequestParam("pageIndex") Optional<Integer> pageIndex,
            @RequestParam("itemCount") Optional<Integer> itemCount
    ) {
        return new ResponseEntity<>(ResponseDto.success("success",
                vAlertFacade.getProjectAlertRecruitList(
                        projectId,
                        pageIndex.orElse(0),
                        itemCount.orElse(6)
                )),
                HttpStatus.OK
        );
    }

    @GetMapping("/recruit/detail")
    public ResponseEntity<ResponseDto<?>> getProjectAlertRecruitDetail(
            @RequestParam("alertId") Long alertId,
            @RequestParam("applyId") Long applyId,
            @RequestParam("voteId") Long voteId
    ){
        return new ResponseEntity<>(ResponseDto.success("success",
                vAlertFacade.getProjectAlertRecruitDetail(alertId, applyId, voteId)),
                HttpStatus.OK
        );
    }

}
