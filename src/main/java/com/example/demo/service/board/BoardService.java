package com.example.demo.service.board;

import com.example.demo.dto.board.response.BoardTotalDetailResponseDto;
import com.example.demo.model.board.Board;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BoardService {

    @Transactional(readOnly = true)
    public BoardSearchPaginationResponseDto search(
            Long positionId, String keyword, List<Long> technologyIds, Pageable pageable);

    public Board findById(Long boardId);

    public Board save(Board board);

    public BoardTotalDetailResponseDto getDetail(Long boardId);

    public void delete(Board board);
}
