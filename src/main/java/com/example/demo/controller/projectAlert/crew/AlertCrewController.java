package com.example.demo.controller.projectAlert.crew;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.service.projectAlert.crew.AlertCrewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/projectAlert/crew")
@RequiredArgsConstructor
public class AlertCrewController {

    public final AlertCrewService alertCrewService;

    @GetMapping("")
    public ResponseEntity<ResponseDto<?>> getProjectAlertCrewList(
            @RequestParam("projectId") Long projectId,
            @RequestParam("pageIndex") Optional<Integer> pageIndex,
            @RequestParam("itemCount") Optional<Integer> itemCount
    ) {
        return new ResponseEntity<>(
                ResponseDto.success("success",
                        alertCrewService.getProjectAlertCrewList(
                                projectId,
                                pageIndex.orElse(0),
                                itemCount.orElse(6))),
                HttpStatus.OK
        );
    }
}
