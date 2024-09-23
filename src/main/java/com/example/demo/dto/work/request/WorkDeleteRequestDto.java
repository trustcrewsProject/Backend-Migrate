package com.example.demo.dto.work.request;

import com.example.demo.constant.ProjectMemberAuth;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkDeleteRequestDto {
    private ProjectMemberAuth authMap;
    private Long workId;
}
