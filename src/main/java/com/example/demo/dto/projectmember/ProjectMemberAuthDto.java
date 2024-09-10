package com.example.demo.dto.projectmember;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectMemberAuthDto<T extends ProjectMemberAuthInterface> {
    private final String code;
    private final String name;
    private final boolean workChangeYN;
    private final boolean milestoneChangeYN;
    private final boolean configYn;

    public ProjectMemberAuthDto(T CONST) {
        this.code = CONST.getCode();
        this.name = CONST.getName();
        this.workChangeYN = CONST.getWorkChangeYN();
        this.milestoneChangeYN = CONST.getMilestoneChangeYN();
        this.configYn = CONST.getConfigYn();
    }

    public ProjectMemberAuthDto(String code, String name, boolean workChangeYN, boolean milestoneChangeYN, boolean configYn) {
        this.code = code;
        this.name = name;
        this.workChangeYN = workChangeYN;
        this.milestoneChangeYN = milestoneChangeYN;
        this.configYn = configYn;
    }

    public static <T extends ProjectMemberAuthInterface> ProjectMemberAuthDto<T> of(T CONST) {
        return ProjectMemberAuthDto.<T>builder()
                .code(CONST.getCode())
                .name(CONST.getName())
                .workChangeYN(CONST.getWorkChangeYN())
                .milestoneChangeYN(CONST.getMilestoneChangeYN())
                .configYn(CONST.getConfigYn())
                .build();
    }
}
