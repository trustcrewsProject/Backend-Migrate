package com.example.demo.trust_grade.service;

import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import com.example.demo.service.trust_grade.TrustGradeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
public class TrustGradeServiceTest {

    @Autowired
    TrustGradeService trustGradeService;

    @Test
    @DisplayName("신뢰등급 전체 조회")
    public void getAllTrustGrades() {
        // when
        List<TrustGradeInfoResponseDto> trustGradeList = trustGradeService.getListByCriteria();

        // then
        Assertions.assertThat(trustGradeList.size()).isEqualTo(4);

    }

}
