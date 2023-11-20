package com.example.demo.model;

import javax.persistence.*;

import com.example.demo.global.common.BaseTimeEntity;
import lombok.*;

@Entity
@Table(name = "board_position")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BoardPosition extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_position_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @Builder
    public BoardPosition(Board board, Position position) {
        this.board = board;
        this.position = position;
    }
}
