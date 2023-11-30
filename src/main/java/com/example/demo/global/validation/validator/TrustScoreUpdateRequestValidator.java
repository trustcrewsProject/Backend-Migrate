package com.example.demo.global.validation.validator;

import com.example.demo.constant.TrustScoreTypeIdentifier;
import com.example.demo.dto.trust_score.request.TrustScoreUpdateRequestDto;
import com.example.demo.global.validation.annotation.ValidTrustScoreUpdateRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TrustScoreUpdateRequestValidator implements ConstraintValidator<ValidTrustScoreUpdateRequest, TrustScoreUpdateRequestDto> {
    @Override
    public void initialize(ValidTrustScoreUpdateRequest constraintAnnotation) {
    }
    @Override
    public boolean isValid(TrustScoreUpdateRequestDto dto, ConstraintValidatorContext context) {
        Long scoreTypeId = dto.getScoreTypeId();
        return scoreTypeId != null && (scoreTypeId.equals(TrustScoreTypeIdentifier.WORK_COMPLETE) ||
                scoreTypeId.equals(TrustScoreTypeIdentifier.WORK_INCOMPLETE));
    }
}
