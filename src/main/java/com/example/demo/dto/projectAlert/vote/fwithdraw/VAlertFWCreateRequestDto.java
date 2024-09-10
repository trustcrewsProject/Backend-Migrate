package com.example.demo.dto.projectAlert.vote.fwithdraw;

import com.example.demo.constant.ProjectFWReason;
import com.example.demo.constant.ProjectMemberAuth;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VAlertFWCreateRequestDto {
    private Long project_id;
    private Long fw_member_id;
    private ProjectMemberAuth fw_member_auth;
    private ProjectMemberAuth authMap;
    private ProjectFWReason reason;

}
