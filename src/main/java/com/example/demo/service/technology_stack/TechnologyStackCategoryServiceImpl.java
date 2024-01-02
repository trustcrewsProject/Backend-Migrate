package com.example.demo.service.technology_stack;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackCategoryInfoResponseDto;
import com.example.demo.model.technology_stack.TechnologyStackCategory;
import com.example.demo.repository.technology_stack.TechnologyStackCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnologyStackCategoryServiceImpl implements TechnologyStackCategoryService {

    private final TechnologyStackCategoryRepository technologyStackCategoryRepository;

    @Override
    public ResponseDto<List<TechnologyStackCategoryInfoResponseDto>> getTechnologyStackCategories() {
        List<TechnologyStackCategoryInfoResponseDto> categories = technologyStackCategoryRepository.findAll().stream()
                .map(technologyStackCategory -> TechnologyStackCategoryInfoResponseDto.of(technologyStackCategory.getId(), technologyStackCategory.getName()))
                .collect(Collectors.toList());

        return ResponseDto.success("기술스택 카테고리 리스트 조회가 완료되었습니다.", categories);
    }
}
