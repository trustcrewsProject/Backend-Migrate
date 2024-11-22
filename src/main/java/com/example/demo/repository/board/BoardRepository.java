package com.example.demo.repository.board;

import com.example.demo.model.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface BoardRepository
        extends JpaRepository<Board, Long>,
                QuerydslPredicateExecutor<Board>,
                BoardRepositoryCustom {

    @Modifying
    @Query("UPDATE Board b SET b.pageView = b.pageView + 1 WHERE b.id = :boardId")
    void increasePageView(@Param("boardId") Long boardId);
}
