package com.example.demo.controller.technology_stack;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.service.technology_stack.TechnologyStackCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TechnologyStackCategoryController {

    private final TechnologyStackCategoryService technologyStackCategoryService;

    @GetMapping("/api/technology-stack-category-list/public")
    public ResponseEntity<ResponseDto<?>> getTechnologyStackCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(technologyStackCategoryService.getTechnologyStackCategories());
    }
}
