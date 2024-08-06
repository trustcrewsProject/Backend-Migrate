package com.example.demo.global.common;

import lombok.Getter;

@Getter
public class ConstantDto<T extends Constant> {

    private final String code;
    private final String name;

    public ConstantDto(T CONST) {
        this.code = CONST.getCode();
        this.name = CONST.getName();
    }

}
