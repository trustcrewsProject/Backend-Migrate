package com.example.demo.model;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "technology_stack")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TechnologyStack {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "technology_stack_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technology_stack_category_id")
    private TechnologyStackCategory technologyStackCategory;
}
