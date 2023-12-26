package com.example.demo.controller.trust_grade;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import com.example.demo.service.trust_grade.TrustGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrustGradeController {

    private final TrustGradeService trustGradeService;

    @GetMapping("/api/trust-grade")
    public ResponseEntity<?> getListByCriteria() {
        List<TrustGradeInfoResponseDto> result = trustGradeService.getListByCriteria();
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }
}
