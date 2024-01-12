package com.example.demo.repository.technology_stack;

import com.example.demo.dto.technology_stack.response.TechnologyStackWithCategoriesResponseDto;

import java.util.List;

public interface TechnologyStackCategoryMappingRepositoryCustom {

    // 각 기술스택 별 카테고리 정보를 포함해 모든 기술스택 조회
    List<TechnologyStackWithCategoriesResponseDto> findAllTechnologyStacksAndCategories();
}
