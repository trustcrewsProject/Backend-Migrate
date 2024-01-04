package com.example.demo.controller.trust_grade;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.trust_grade.TrustGradeService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/api/trust-grade/me")
    public ResponseEntity<?> getUserPossibleGrades(@AuthenticationPrincipal PrincipalDetails user) {
        List<TrustGradeInfoResponseDto> result = trustGradeService.getPossibleUserGrades(user.getId());
        return new ResponseEntity<>(ResponseDto.success("success", result), HttpStatus.OK);
    }
}
