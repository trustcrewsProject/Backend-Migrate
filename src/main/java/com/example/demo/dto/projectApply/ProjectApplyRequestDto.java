package com.example.demo.dto.projectApply;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectApplyRequestDto {
    private long projectId;
    private long positionId;
    private String apply_message;
}
