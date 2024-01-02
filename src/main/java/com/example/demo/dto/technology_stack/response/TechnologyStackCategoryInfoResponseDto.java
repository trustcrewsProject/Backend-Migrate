package com.example.demo.dto.technology_stack.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TechnologyStackCategoryInfoResponseDto {

    private Long techStackCategoryId;

    private String techStackCategoryName;

    public static TechnologyStackCategoryInfoResponseDto of (Long id, String name) {
        return TechnologyStackCategoryInfoResponseDto.builder()
                .techStackCategoryId(id)
                .techStackCategoryName(name)
                .build();
    }
}
