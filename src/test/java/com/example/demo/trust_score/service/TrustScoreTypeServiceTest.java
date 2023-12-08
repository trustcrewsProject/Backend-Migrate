package com.example.demo.trust_score.service;

import com.example.demo.dto.trust_score_type.response.TrustScoreTypeReadResponseDto;
import com.example.demo.service.trust_score.TrustScoreTypeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@SpringBootTest
public class TrustScoreTypeServiceTest {

    @Autowired
    private TrustScoreTypeService trustScoreTypeService;

    @Test
    @DisplayName("신뢰점수타입 전체 DTO로 변환")
    public void getAllAndReturnDto_Method_Test_Pass() {

        // when
        List<TrustScoreTypeReadResponseDto> getAllAndReturnDto =
                trustScoreTypeService.getAllAndReturnDto();

        // then
        Assertions.assertThat(getAllAndReturnDto.size()).isEqualTo(26);
    }
}
