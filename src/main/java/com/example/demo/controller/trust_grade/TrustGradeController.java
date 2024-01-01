package com.example.demo.controller.trust_grade;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import com.example.demo.service.trust_grade.TrustGradeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TrustGradeController {

    private final TrustGradeService trustGradeService;

    @GetMapping("/api/trust-grade")
    public ResponseEntity<?> getListByCriteria() {
        List<TrustGradeInfoResponseDto> result = trustGradeService.getListByCriteria();
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }

    @GetMapping("/api/trust-grade/user/{userId}")
    public ResponseEntity<?> getUserPossibleGrades(@PathVariable(name = "userId") Long userId) {
        List<TrustGradeInfoResponseDto> result = trustGradeService.getPossibleUserGrades(userId);
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }
}
