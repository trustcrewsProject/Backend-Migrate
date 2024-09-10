package com.example.demo.dto.project.request;

import com.example.demo.constant.ProjectMemberAuth;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WithdrawRequestDto {
    private Long projectId;
    private Long wMemberId;
    private ProjectMemberAuth wMemberAuth;
}
