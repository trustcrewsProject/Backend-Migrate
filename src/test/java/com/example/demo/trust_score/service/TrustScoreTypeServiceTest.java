package com.example.demo.trust_score.service;

import com.example.demo.constant.TrustScoreTypeIdentifier;
import com.example.demo.dto.trust_score_type.request.TrustScoreTypeCreateRequestDto;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeCreateResponseDto;
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

    @Test
    @DisplayName("상위신뢰점수타입 생성")
    public void createTrustScoreType_UpScoreType_Method_Test_Pass() {
        // given
        TrustScoreTypeCreateRequestDto requestDto = new TrustScoreTypeCreateRequestDto();
        requestDto.setUpTrustScoreTypeId(null);
        requestDto.setScore(null);
        requestDto.setDeleteStatus("n");
        requestDto.setGubunCode("p");
        requestDto.setTrustGradeName(null);
        requestDto.setTrustScoreTypeName("테스트 신뢰점수타입");

        // when
        TrustScoreTypeCreateResponseDto responseDto = trustScoreTypeService.createTrustScoreType(requestDto);

        // then
        Assertions.assertThat(responseDto.getScore()).isNull();
        Assertions.assertThat(responseDto.getUpTrustScoreTypeName()).isNull();
        Assertions.assertThat(responseDto.getCreateDate()).isNotNull();
        Assertions.assertThat(responseDto.getId()).isGreaterThan(26);
        Assertions.assertThat(responseDto.getGubunCode()).isEqualTo("P");
        Assertions.assertThat(responseDto.getDeleteStatus()).isEqualTo("N");
    }

    @Test
    @DisplayName("하위신뢰점수타입 생성")
    public void createTrustScoreType_LowScoreType_Method_Test_Pass() {
        // given
        TrustScoreTypeCreateRequestDto requestDto = new TrustScoreTypeCreateRequestDto();
        requestDto.setUpTrustScoreTypeId(TrustScoreTypeIdentifier.WORK_INCOMPLETE);
        requestDto.setScore(700);
        requestDto.setDeleteStatus("n");
        requestDto.setGubunCode("m");
        requestDto.setTrustGradeName("테스트 신뢰등급");
        requestDto.setTrustScoreTypeName("테스트 신뢰등급 업무 미흡");

        // when
        TrustScoreTypeCreateResponseDto responseDto = trustScoreTypeService.createTrustScoreType(requestDto);

        // then
        Assertions.assertThat(responseDto.getScore()).isEqualTo(700);
        Assertions.assertThat(responseDto.getUpTrustScoreTypeName()).isEqualTo("업무 미흡");
        Assertions.assertThat(responseDto.getCreateDate()).isNotNull();
        Assertions.assertThat(responseDto.getId()).isGreaterThan(26);
        Assertions.assertThat(responseDto.getGubunCode()).isEqualTo("M");
        Assertions.assertThat(responseDto.getDeleteStatus()).isEqualTo("N");
    }
}
