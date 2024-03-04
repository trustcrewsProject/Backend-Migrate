package com.example.demo.service.board;

import com.example.demo.dto.board.response.BoardTotalDetailResponseDto;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.model.board.Board;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BoardService {

    @Transactional(readOnly = true)
    PaginationResponseDto search(Long positionId, String keyword, List<Long> technologyIds, Pageable pageable);

    Board findById(Long boardId);

    Board save(Board board);

    BoardTotalDetailResponseDto getDetail(Long boardId);

    void delete(Board board);

    @Transactional
    void updateRecruitmentStatus(Long boardId, Long userId);
}
