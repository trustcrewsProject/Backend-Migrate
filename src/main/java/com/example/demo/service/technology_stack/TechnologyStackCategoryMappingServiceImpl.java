package com.example.demo.service.technology_stack;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackWithCategoriesResponseDto;
import com.example.demo.repository.technology_stack.TechnologyStackCategoryMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TechnologyStackCategoryMappingServiceImpl implements TechnologyStackCategoryMappingService{

    private final TechnologyStackCategoryMappingRepository technologyStackCategoryMappingRepository;

    @Override
    public ResponseDto<?> getCategoriesByTechStacks() {
        List<TechnologyStackWithCategoriesResponseDto> techStacks = technologyStackCategoryMappingRepository.findAllTechnologyStacksAndCategories();
        return ResponseDto.success("모든 기술스택별 카테고리 목록 조회가 완료되었습니다.", techStacks);
    }
}
