package com.example.demo.dto.projectAlert.vote.fwithdraw;

import com.example.demo.constant.ProjectFWReason;
import com.example.demo.dto.project.ProjectDetailAuthDto;
import com.example.demo.model.project.ProjectMemberAuth;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VAlertFWCreateRequestDto {
    private Long project_id;
    private Long fw_member_id;
    private ProjectMemberAuth fw_member_auth;
    private ProjectDetailAuthDto authMap;
    private ProjectFWReason reason;

}
