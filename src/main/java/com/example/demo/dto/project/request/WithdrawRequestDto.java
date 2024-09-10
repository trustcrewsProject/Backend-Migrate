package com.example.demo.dto.project.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WithdrawRequestDto {
    private Long projectId;
    private Long wMemberId;
    private Long wMemberAuthId;
}
