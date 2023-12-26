package com.example.demo.trust_grade.controller;


import com.example.demo.controller.trust_grade.TrustGradeController;
import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@Transactional
public class TrustGradeControllerTest {

    @Autowired TrustGradeController trustGradeController;

    @Test
    @DisplayName("전체 신뢰등급 조회 - 컨트롤러")
    public void getListByCriteria_Controller() {
        // when
        List<TrustGradeInfoResponseDto> trustGradeList = trustGradeController.getListByCriteria();

        // then
        Assertions.assertThat(trustGradeList.size()).isEqualTo(4);

    }
}
