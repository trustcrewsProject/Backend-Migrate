package com.example.demo.repository.board;

import com.example.demo.model.board.BoardPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardPositionRepository extends JpaRepository<BoardPosition, Long> {
    void deleteBoardPositionByBoard_Id(Long boardId);
}
