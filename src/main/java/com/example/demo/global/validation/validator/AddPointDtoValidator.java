package com.example.demo.global.validation.validator;

import com.example.demo.dto.trust_score.AddPointDto;
import com.example.demo.global.validation.annotation.ValidAddPointDto;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.example.demo.constant.TrustScoreTypeIdentifier.*;

public class AddPointDtoValidator implements ConstraintValidator<ValidAddPointDto, AddPointDto> {
    @Override
    public void initialize(ValidAddPointDto constraintAnnotation) {
    }

    /**
     * AddPointDto 유효성 검증(validate)
     * @param dto object to validate
     * @param context context in which the constraint is evaluated
     * @return
     */
    @Override
    public boolean isValid(AddPointDto dto, ConstraintValidatorContext context) {
        Long scoreTypeId = dto.getScoreTypeId();
        Long projectId = dto.getProjectId();
        Long milestoneId = dto.getMilestoneId();
        Long workId = dto.getWorkId();
        if (scoreTypeId.equals(WORK_COMPLETE)) {
            return projectId != null && milestoneId != null && workId != null;
        } else if (scoreTypeId.equals(WORK_INCOMPLETE)) {
            return projectId != null && milestoneId != null && workId != null;
        } else if (scoreTypeId.equals(NEW_MEMBER)) {
            return projectId == null && milestoneId == null && workId == null;
        } else if (scoreTypeId.equals(SELF_WITHDRAWAL)) {
            return projectId != null && milestoneId == null && workId == null;
        } else if (scoreTypeId.equals(FORCE_WITHDRAWAL)) {
            return projectId != null && milestoneId == null && workId == null;
        } else {
            return false;
        }
    }
}