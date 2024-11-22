package com.example.demo.repository.board;

import com.example.demo.constant.ProjectStatus;
import com.example.demo.dto.common.PaginationResponseDto;
import com.example.demo.model.board.Board;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardRepositoryCustom {

    PaginationResponseDto getBoardSearchPage(
            Long positionId, String keyword, List<Long> technologyIds, Boolean recruitmentStatus, ProjectStatus status, Pageable pageable);

    Board findByProjectId(Long projectId);

}
