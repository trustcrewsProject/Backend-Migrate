package com.example.demo.global.validation.validator;

import com.example.demo.dto.trust_score.request.TrustScoreUpdateRequestDto;
import com.example.demo.global.validation.annotation.ValidTrustScoreUpdateRequest;
import static com.example.demo.constant.TrustScoreTypeIdentifier.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

// TODO : Custom Exception 생성
public class TrustScoreUpdateRequestValidator implements ConstraintValidator<ValidTrustScoreUpdateRequest, TrustScoreUpdateRequestDto> {
    @Override
    public void initialize(ValidTrustScoreUpdateRequest constraintAnnotation) {
    }
    @Override
    public boolean isValid(TrustScoreUpdateRequestDto dto, ConstraintValidatorContext context) {
        Long scoreTypeId = dto.getScoreTypeId();

        if (scoreTypeId == null) {
            throw new RuntimeException("scoreTypeId 입력값이 null임");
        }

        boolean validScoreTypeId =  scoreTypeId.equals(WORK_COMPLETE)
                || scoreTypeId.equals(WORK_INCOMPLETE);

        if (!validScoreTypeId) {
            throw new RuntimeException("API 호출은 1L, 2L만 가능함");
        }
        return true;
    }
}
