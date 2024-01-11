package com.example.demo.dto.common;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PaginationResponseDto {

    private List<?> content;
    private long totalPages;

    public static PaginationResponseDto of(List<?> content, long totalPages) {
        return PaginationResponseDto.builder()
                .content(content)
                .totalPages(totalPages)
                .build();
    }
}
