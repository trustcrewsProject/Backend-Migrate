package com.example.demo.controller.trust_score;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.trust_score_type.TrustScoreTypeSearchCriteria;
import com.example.demo.dto.trust_score_type.request.TrustScoreTypeCreateRequestDto;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeCreateResponseDto;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeReadResponseDto;
import com.example.demo.service.trust_score.TrustScoreTypeService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TrustScoreTypeController {
    private final TrustScoreTypeService trustScoreTypeService;

    @GetMapping("/api/trust-score-type")
    public ResponseEntity<ResponseDto<?>> getAll() {
        List<TrustScoreTypeReadResponseDto> dto = trustScoreTypeService.getAllAndReturnDto();
        return new ResponseEntity<>(ResponseDto.success("success", dto), HttpStatus.OK);
    }

    @GetMapping("/api/trust-score-type/search")
    public ResponseEntity<ResponseDto<?>> getSearchResults(
            @RequestParam(name = "isDeleted", required = false) Boolean isDeleted,
            @RequestParam(name = "isParentType", required = false) Boolean isParentType,
            @RequestParam(name = "trustGrade", required = false) List<String> trustGrade,
            @RequestParam(name = "parentTypeId", required = false) List<Long> parentTypeId,
            @RequestParam(name = "gubunCode", required = false) String gubunCode,
            @RequestParam(name = "keyword", required = false) String keyword) {
        TrustScoreTypeSearchCriteria criteria =
                TrustScoreTypeSearchCriteria.builder()
                        .isParentType(isDeleted)
                        .isParentType(isParentType)
                        .trustGrade(trustGrade)
                        .parentTypeId(parentTypeId)
                        .gubunCode(gubunCode)
                        .keyword(keyword)
                        .build();
        List<TrustScoreTypeReadResponseDto> searchResults =
                trustScoreTypeService.getSearchResults(criteria);
        return new ResponseEntity<>(ResponseDto.success("success", searchResults), HttpStatus.OK);
    }

    /** 더블 클릭했을 때 팝업으로 정보가 나옴 */
    @GetMapping("/api/trust-score-type/{trustScoreTypeId}")
    public ResponseEntity<ResponseDto<?>> getSearchResults(
            @PathVariable(name = "trustScoreTypeId") Long trustScoreTypeId) {
        TrustScoreTypeReadResponseDto responseDto =
                trustScoreTypeService.findByIdAndReturnDto(trustScoreTypeId);
        return new ResponseEntity<>(ResponseDto.success("success", responseDto), HttpStatus.OK);
    }

    @PostMapping("/api/trust-score-type")
    public ResponseEntity<ResponseDto<?>> createTrustScoreType(
            @RequestBody @Valid TrustScoreTypeCreateRequestDto requestDto) {
        TrustScoreTypeCreateResponseDto responseDto =
                trustScoreTypeService.createTrustScoreType(requestDto);

        return new ResponseEntity<>(ResponseDto.success("success", responseDto), HttpStatus.OK);
    }
}
