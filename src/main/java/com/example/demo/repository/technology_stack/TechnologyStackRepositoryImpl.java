package com.example.demo.repository.technology_stack;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TechnologyStackRepositoryImpl implements TechnologyStackRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
}
