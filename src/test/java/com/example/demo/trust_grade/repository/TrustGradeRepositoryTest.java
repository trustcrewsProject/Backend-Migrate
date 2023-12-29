package com.example.demo.trust_grade.repository;

import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import com.example.demo.repository.trust_grade.TrustGradeRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class TrustGradeRepositoryTest {

    @Autowired TrustGradeRepository trustGradeRepository;

    @Test
    @DisplayName("전체 신뢰등급 조회 - 리포지토리")
    public void getListByCriteria_Repository() {
        // when
        List<TrustGradeInfoResponseDto> trustGradeList =
                trustGradeRepository.getListByCriteria(null);

        // then
        Assertions.assertThat(trustGradeList.size()).isEqualTo(4);
    }
}
