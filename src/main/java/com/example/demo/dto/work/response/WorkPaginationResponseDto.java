package com.example.demo.dto.work.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class WorkPaginationResponseDto {

    private List<WorkReadResponseDto> content;
    private long totalPages;

    public static WorkPaginationResponseDto of(List<WorkReadResponseDto> content, long totalPages) {
        return WorkPaginationResponseDto.builder()
                .content(content)
                .totalPages(totalPages)
                .build();
    }
}
