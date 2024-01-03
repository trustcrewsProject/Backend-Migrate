package com.example.demo.repository.technology_stack;

import com.example.demo.dto.technology_stack.response.TechnologyStackWithCategoriesResponseDto;
import com.example.demo.model.technology_stack.QTechnologyStack;
import com.example.demo.model.technology_stack.QTechnologyStackCategory;
import com.example.demo.model.technology_stack.QTechnologyStackCategoryMapping;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class TechnologyStackCategoryMappingRepositoryImpl implements TechnologyStackCategoryMappingRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    private final QTechnologyStack qTechnologyStack = QTechnologyStack.technologyStack;
    private final QTechnologyStackCategory qTechnologyStackCategory = QTechnologyStackCategory.technologyStackCategory;
    private final QTechnologyStackCategoryMapping qTechnologyStackCategoryMapping = QTechnologyStackCategoryMapping.technologyStackCategoryMapping;

    @Override
    public List<TechnologyStackWithCategoriesResponseDto> findAllTechnologyStacksAndCategories() {
       List<Tuple> tuples = jpaQueryFactory
               .select(qTechnologyStack.id, qTechnologyStack.name, qTechnologyStackCategory.name)
               .from(qTechnologyStackCategoryMapping)
               .join(qTechnologyStackCategoryMapping.technologyStack, qTechnologyStack)
               .join(qTechnologyStackCategoryMapping.category, qTechnologyStackCategory)
               .fetch();

       // 조회한 기술스택 정보를 ID, name 형태로 Map 으로 관리
       Map<Long, String> techStackNames = tuples.stream()
               .collect(Collectors.toMap(
                       tuple -> tuple.get(qTechnologyStack.id),
                       tuple -> tuple.get(qTechnologyStack.name),
                       (name1, name2) -> name1
               ));

       // 조회한 기술스택, 카테고리 결과를 기술스택 ID를 기준으로 그룹화를 진행하고 TechnologyStackWithCategoriesResponseDto 셋팅
       List<TechnologyStackWithCategoriesResponseDto> results = tuples.stream()
               .collect(Collectors.collectingAndThen(
                       Collectors.groupingBy(
                               tuple -> tuple.get(qTechnologyStack.id),
                               Collectors.mapping(
                                       tuple -> tuple.get(qTechnologyStackCategory.name),
                                       Collectors.toList()
                               )
                       ),
                       techStacksWithCategories -> techStacksWithCategories.entrySet().stream()
                               .map(entry -> {
                                   Long techStackId = entry.getKey();
                                   String techStackName = techStackNames.getOrDefault(techStackId, "");
                                   List<String> categories = entry.getValue();

                                   return TechnologyStackWithCategoriesResponseDto.of(techStackId, techStackName, categories);
                               })
                               .collect(Collectors.toList())
               ));

       return results;
    }
}
