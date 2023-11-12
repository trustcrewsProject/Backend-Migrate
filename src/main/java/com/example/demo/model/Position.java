package com.example.demo.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "position")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "position", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<BoardPosition> board = new ArrayList<>();
}
