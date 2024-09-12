package com.example.demo.constant;

import com.example.demo.dto.projectmember.ProjectMemberAuthInterface;
import com.example.demo.global.exception.customexception.ProjectMemberAuthCustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum ProjectMemberAuth implements ProjectMemberAuthInterface {

    PAUTH_1001("PAUTH_1001", "매니저", true, true, true),
    PAUTH_2001("PAUTH_2001", "크루", true, false, false);

    private final String code;
    private final String name;
    private final boolean workChangeYN;
    private final boolean milestoneChangeYN;
    private final boolean configYn;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isMilestoneChangeYN() {
        return this.milestoneChangeYN;
    }

    @Override
    public boolean isWorkChangeYN() {
        return this.workChangeYN;
    }

    @Override
    public boolean isConfigYn() {
        return this.configYn;
    }
}
