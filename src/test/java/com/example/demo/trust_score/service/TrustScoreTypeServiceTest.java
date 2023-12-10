package com.example.demo.trust_score.service;

import com.example.demo.dto.trust_score_type.response.TrustScoreTypeReadResponseDto;
import com.example.demo.model.trust_score.TrustScoreType;
import com.example.demo.repository.trust_score.TrustScoreTypeRepository;
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

    @Autowired
    private TrustScoreTypeRepository trustScoreTypeRepository;

    @Test
    @DisplayName("신뢰점수타입 전체 DTO 반환")
    public void getAllAndReturnDto_Method_Test_Pass() {

        // when
        List<TrustScoreTypeReadResponseDto> getAllAndReturnDto =
                trustScoreTypeService.getAllAndReturnDto();

        // then
        Assertions.assertThat(getAllAndReturnDto.size()).isEqualTo(26);
    }

    @Test
    @DisplayName("개별 신뢰점수타입 DTO 반환")
    public void findByIdAndReturnDto_Method_Test_Pass() {
        // given
        TrustScoreType trustScoreType = TrustScoreType.builder()
                            .upTrustScoreType(null)
                            .trustScoreTypeName("테스트 신뢰정보타입")
                            .trustGradeName("테스트 신뢰등급")
                            .gubunCode("테스트 구분코드")
                            .score(77777)
                            .deleteStatus("N")
                            .build();


        // when

        TrustScoreType saveTrustScoreType = trustScoreTypeRepository.save(trustScoreType);
        TrustScoreTypeReadResponseDto responseDto = trustScoreTypeService.findByIdAndReturnDto(saveTrustScoreType.getId());

        // then
        Assertions.assertThat(responseDto.getTrustScoreTypeId()).isGreaterThan(26);
        Assertions.assertThat(responseDto.getTrustScoreTypeName()).isEqualTo("테스트 신뢰정보타입");
    }
}
