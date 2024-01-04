package com.example.demo.dto.user.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserProjectHistoryPaginationResponseDto {

    private List<UserProjectHistoryInfoResponseDto> content;
    private long totalPages;

    public static UserProjectHistoryPaginationResponseDto of(List<UserProjectHistoryInfoResponseDto> content, long totalPages) {
        return UserProjectHistoryPaginationResponseDto.builder()
                .content(content)
                .totalPages(totalPages)
                .build();
    }
}
