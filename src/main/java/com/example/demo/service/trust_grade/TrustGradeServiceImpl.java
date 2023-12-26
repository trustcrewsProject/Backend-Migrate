package com.example.demo.service.trust_grade;

import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import com.example.demo.global.exception.customexception.TrustGradeCustomException;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.repository.trust_grade.TrustGradeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrustGradeServiceImpl implements TrustGradeService {
    private final TrustGradeRepository trustGradeRepository;

    @Override
    public TrustGrade getTrustGradeById(Long id) {
        return trustGradeRepository
                .findById(id)
                .orElseThrow(() -> TrustGradeCustomException.NOT_FOUND_TRUST_GRADE);
    }

    @Override
    public List<TrustGradeInfoResponseDto> getListByCriteria() {
        return trustGradeRepository.getListByCriteria(null);
    }
}
