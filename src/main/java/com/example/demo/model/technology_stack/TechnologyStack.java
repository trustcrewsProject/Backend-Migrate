package com.example.demo.model.technology_stack;

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

    private String name;
}
