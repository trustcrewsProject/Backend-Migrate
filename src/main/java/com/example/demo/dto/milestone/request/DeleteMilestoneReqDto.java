package com.example.demo.dto.milestone.request;

import com.example.demo.constant.ProjectMemberAuth;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteMilestoneReqDto {
    private Long milestoneId;
    private Long projectId;
    private ProjectMemberAuth authMap;
}
