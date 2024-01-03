package com.example.demo.repository.technology_stack;

import com.example.demo.model.technology_stack.TechnologyStackCategoryMapping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyStackCategoryMappingRepository extends JpaRepository<TechnologyStackCategoryMapping, Long>, TechnologyStackCategoryMappingRepositoryCustom {
}
