package com.example.demo.service.technology_stack;

import com.example.demo.dto.common.ResponseDto;

public interface TechnologyStackCategoryMappingService {

    // 모든 기술스택 별 카테고리 조회
    ResponseDto<?> getCategoriesByTechStacks();
}
