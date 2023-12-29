package com.example.demo.repository.trust_grade;

import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import com.example.demo.model.trust_grade.QTrustGrade;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TrustGradeRepositoryImpl implements TrustGradeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TrustGradeInfoResponseDto> getListByCriteria(BooleanExpression builder) {
        QTrustGrade trustGrade = QTrustGrade.trustGrade;
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                TrustGradeInfoResponseDto.class, trustGrade.id, trustGrade.name))
                .from(trustGrade)
                .where(builder)
                .fetch();
    }
}
