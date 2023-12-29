package com.example.demo.repository.trust_grade;

import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.List;

public interface TrustGradeRepositoryCustom {

    List<TrustGradeInfoResponseDto> getListByCriteria(BooleanExpression builder);
}
