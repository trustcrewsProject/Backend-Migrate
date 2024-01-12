package com.example.demo.service.trust_grade;

import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import com.example.demo.model.trust_grade.TrustGrade;
import java.util.List;

public interface TrustGradeService {

    TrustGrade getTrustGradeById(Long id);

    List<TrustGradeInfoResponseDto> getListByCriteria();

    List<TrustGradeInfoResponseDto> getPossibleUserGrades(Long userId);
}
