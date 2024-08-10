package com.example.demo.constant;

import com.example.demo.global.common.Constant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum VoteStatus implements Constant {
    VSTAT1001("VSTAT1001", "투표중"),
    VSTAT1002("VSTAT1002", "투표종료");

    private String code;
    private String name;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
