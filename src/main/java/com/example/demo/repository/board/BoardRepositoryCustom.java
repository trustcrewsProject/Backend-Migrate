package com.example.demo.repository.board;

import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    BoardSearchPaginationResponseDto getBoardSearchPage(
            Long positionId, String keyword, List<Long> technologyIds, Pageable pageable);
}
