package com.example.demo.dto.technology_stack.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TechnologyStackWithCategoriesResponseDto {

    private Long techStackId;
    private String techStackName;
    private List<String> categories;

    public static TechnologyStackWithCategoriesResponseDto of(Long techStackId, String techStackName, List<String> categories) {
        return TechnologyStackWithCategoriesResponseDto.builder()
                .techStackId(techStackId)
                .techStackName(techStackName)
                .categories(categories)
                .build();
    }
}
