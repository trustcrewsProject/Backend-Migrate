package com.example.demo.controller.technology_stack;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.service.technology_stack.TechnologyStackCategoryMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TechnologyStackCategoryMappingController {

    private final TechnologyStackCategoryMappingService technologyStackCategoryMappingService;

    @GetMapping("/api/technology-stack-category-mapping-list/public")
    public ResponseEntity<ResponseDto<?>> getTechnologyStacksWithCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(technologyStackCategoryMappingService.getCategoriesByTechStacks());
    }
}
