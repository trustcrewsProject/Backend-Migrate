package com.example.demo.controller.trust_score;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeReadResponseDto;
import com.example.demo.service.trust_score.TrustScoreService;
import com.example.demo.service.trust_score.TrustScoreTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrustScoreTypeController {
    private final TrustScoreTypeService trustScoreTypeService;
    @GetMapping("/api/trust-score-type")
    public ResponseEntity<ResponseDto<?>> getAll() {
        List<TrustScoreTypeReadResponseDto> dto = trustScoreTypeService.getAllAndReturnDto();
        return new ResponseEntity<>(ResponseDto.success("success",dto), HttpStatus.OK);
    }
}
