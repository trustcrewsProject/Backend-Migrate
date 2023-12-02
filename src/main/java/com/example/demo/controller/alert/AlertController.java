package com.example.demo.controller.alert;

import com.example.demo.dto.alert.AlertCreateRequestDto;
import com.example.demo.dto.common.ResponseDto;
import com.example.demo.service.alert.AlertFacade;
import com.example.demo.service.alert.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlertController {

    private final AlertFacade alertFacade;
    private final AlertService alertService;

    @PostMapping("/api/alert/}")
    public ResponseEntity<ResponseDto<?>> send(AlertCreateRequestDto alertCreateRequestDto){
        alertFacade.send(alertCreateRequestDto);
        return new ResponseEntity<>(ResponseDto.success("success"), HttpStatus.OK);
    }
}
