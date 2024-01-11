package com.example.demo.dto.common;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PaginationResponseDto<T> {

    private List<T> content;
    private long totalPages;

    public static <T> PaginationResponseDto<T> of(List<T> content, long totalPages) {
        return PaginationResponseDto.<T>builder()
                .content(content)
                .totalPages(totalPages)
                .build();
    }
}
