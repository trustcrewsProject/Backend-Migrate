package com.example.demo.dto.common;

import com.example.demo.global.common.Constant;
import com.example.demo.global.common.ConstantDepth;
import lombok.Getter;

@Getter
public class ConstantDepthDto<T extends ConstantDepth> {
    private final String code;
    private final String name;
    private final Object parent;

    public ConstantDepthDto(T CONST) {
        this.code = CONST.getCode();
        this.name = CONST.getName();
        this.parent = CONST.getParent();
    }
}
