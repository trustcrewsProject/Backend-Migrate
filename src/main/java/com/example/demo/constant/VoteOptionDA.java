package com.example.demo.constant;

import com.example.demo.global.common.Constant;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum VoteOptionDA implements Constant {
    VODA1001("VODA1001", "찬성"),
    VODA1002("VODA1002", "반대");

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
