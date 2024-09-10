package com.example.demo.dto.project.setting.request;

import com.example.demo.constant.ProjectMemberAuth;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectSettingCrewAuthUpdReqDto {
    private ProjectMemberAuth authMap;
    private Long projectId;
    private Long projectMemberId;
    private ProjectMemberAuth projectMemberAuth;
}
