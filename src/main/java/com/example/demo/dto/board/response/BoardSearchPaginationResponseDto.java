package com.example.demo.dto.board.response;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class BoardSearchPaginationResponseDto {

    private List<BoardSearchResponseDto> content;
    private long totalPages;

    public static BoardSearchPaginationResponseDto of(List<BoardSearchResponseDto> content, long totalPages) {
        return BoardSearchPaginationResponseDto.builder()
                .content(content)
                .totalPages(totalPages)
                .build();
    }

}
