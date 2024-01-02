package com.example.demo.service.technology_stack;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackCategoryInfoResponseDto;

import java.util.List;

public interface TechnologyStackCategoryService {

    // 기술스택 카테고리 목록 조회
    ResponseDto<List<TechnologyStackCategoryInfoResponseDto>> getTechnologyStackCategories();
}
