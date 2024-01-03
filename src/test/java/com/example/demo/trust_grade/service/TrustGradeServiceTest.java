package com.example.demo.trust_grade.service;

import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import com.example.demo.model.trust_score.TrustScore;
import com.example.demo.model.user.User;
import com.example.demo.repository.trust_score.TrustScoreRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.trust_grade.TrustGradeService;
import java.util.List;
import javax.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class TrustGradeServiceTest {

    @Autowired
    private TrustGradeService trustGradeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrustScoreRepository trustScoreRepository;

    @Test
    @DisplayName("신뢰등급 전체 조회")
    public void getAllTrustGrades() {
        // when
        List<TrustGradeInfoResponseDto> trustGradeList = trustGradeService.getListByCriteria();

        // then
        Assertions.assertThat(trustGradeList.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("회원 지원 가능 신뢰등급 조회")
    public void getPossibleTrustGrades() {
        // given
        User user = User.builder()
                .email("aaa@gmail.com")
                .build();
        TrustScore trustScore = TrustScore.builder()
                .score(1500)
                .build();
        trustScoreRepository.save(trustScore);
        user.setTrustScore(trustScore);
        User saveUser = userRepository.save(user);


        // when
        List<TrustGradeInfoResponseDto> grades =
                trustGradeService.getPossibleUserGrades(saveUser.getId());

        // then
        Assertions.assertThat(grades.size()).isEqualTo(4);

    }
}
