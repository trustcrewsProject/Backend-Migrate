package com.example.demo.constant;

import com.example.demo.global.common.ConstantDepth;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ProjectAlertType implements ConstantDepth {
    PRA1001("PRA1001", "투표", null),
    PRA1002("PRA1002", "모집", PRA1001),
    PRA1003("PRA1003", "강제탈퇴", PRA1001),
    PRA2001("PRA2001", "크루", null),
    PRA3001("PRA3001", "업무", null);

    private final String code;
    private final String name;

    private final ProjectAlertType parent;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ProjectAlertType getParent() {
        return this.parent;
    }
}
