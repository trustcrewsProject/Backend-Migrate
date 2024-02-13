package com.example.demo.repository.board;

import java.util.List;

import com.example.demo.constant.ProjectStatus;
import com.example.demo.dto.common.PaginationResponseDto;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {

    PaginationResponseDto getBoardSearchPage(
            Long positionId, String keyword, List<Long> technologyIds, Boolean recruitmentStatus, ProjectStatus status, Pageable pageable);
}
