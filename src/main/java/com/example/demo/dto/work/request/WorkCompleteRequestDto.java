package com.example.demo.dto.work.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkCompleteRequestDto {
    private Long userId;
    private Long projectId;
    private Long milestoneId;
    private Long workId;
    private String content;
}
