package com.example.demo.model.technology_stack;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "technology_stack_category_mapping")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TechnologyStackCategoryMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "technology_stack_category_mapping_id")
    private Long technologyStackCategoryMappingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technology_stack_id")
    private TechnologyStack technologyStack;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technology_stack_category_id")
    private TechnologyStackCategory category;
}
