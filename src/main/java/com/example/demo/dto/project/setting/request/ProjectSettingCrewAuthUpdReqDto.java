package com.example.demo.dto.project.setting.request;

import com.example.demo.dto.project.ProjectDetailAuthDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectSettingCrewAuthUpdReqDto {
    private ProjectDetailAuthDto authMap;
    private Long projectId;
    private Long projectMemberId;
    private Long projectMemberAuthId;
}
