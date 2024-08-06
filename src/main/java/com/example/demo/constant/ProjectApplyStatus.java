package com.example.demo.constant;

import com.example.demo.global.common.Constant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProjectApplyStatus implements Constant {
    PAS1001("PAS1001", "확인중"),
    PAS1002("PAS1002", "수락"),
    PAS1003("PAS1003", "거절");

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
