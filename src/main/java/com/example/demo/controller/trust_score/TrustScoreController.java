package com.example.demo.controller.trust_score;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.trust_score.request.TrustScoreUpdateRequestDto;
import com.example.demo.dto.trust_score.response.TrustScoreUpdateResponseDto;
import com.example.demo.service.trust_score.TrustScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TrustScoreController {
    private final TrustScoreService trustScoreService;
    @PostMapping("/api/trust-score")
    public ResponseEntity<ResponseDto<?>> updateScoreByManager(
            @RequestBody @Valid TrustScoreUpdateRequestDto requestDto) {
        TrustScoreUpdateResponseDto responseDto = trustScoreService.updateTrustScore(requestDto);
        return new ResponseEntity<>(new ResponseDto<>("HttpStatusOK", "success", responseDto), HttpStatus.OK);
    }
}
