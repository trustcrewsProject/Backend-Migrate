package com.example.demo.constant;

import com.example.demo.global.common.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum UserProjectHistoryStatus implements Constant {
    PHIST_STAT_001("PHIST_STAT_001","프로젝트 생성"),
    PHIST_STAT_002("PHIST_STAT_002","프로젝트 참여"),
    PHIST_STAT_003("PHIST_STAT_003", "프로젝트 완주"),
    PHIST_STAT_004("PHIST_STAT_004", "프로젝트 탈퇴"),
    PHIST_STAT_005("PHIST_STAT_005", "프로젝트 강제탈퇴");

    private final String code;
    private final String name;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
