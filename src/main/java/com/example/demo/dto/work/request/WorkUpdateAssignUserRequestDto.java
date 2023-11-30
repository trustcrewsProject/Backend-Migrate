package com.example.demo.dto.work.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkUpdateAssignUserRequestDto {
    private Long assignedUserId;
}
