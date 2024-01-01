package com.example.demo.service.trust_grade;

import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import com.example.demo.global.exception.customexception.TrustGradeCustomException;
import com.example.demo.global.exception.customexception.UserCustomException;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.model.user.User;
import com.example.demo.repository.trust_grade.TrustGradeRepository;
import java.util.List;

import com.example.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrustGradeServiceImpl implements TrustGradeService {
    private final TrustGradeRepository trustGradeRepository;
    private final UserRepository userRepository;

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

    @Override
    public List<TrustGradeInfoResponseDto> getPossibleUserGrades(Long userId) {
        User findUser = userRepository.findById(userId)
                .orElseThrow(() -> UserCustomException.NOT_FOUND_USER);
        return trustGradeRepository.getPossibleTrustGrades(findUser.getTrustScore().getScore());
    }
}
