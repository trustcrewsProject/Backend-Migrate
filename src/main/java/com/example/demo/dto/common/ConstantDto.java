package com.example.demo.dto.common;

import com.example.demo.global.common.Constant;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConstantDto<T extends Constant> {
    private final String code;
    private final String name;

    public ConstantDto(T CONST){
        this.code = CONST.getCode();
        this.name = CONST.getName();
    }

    public ConstantDto(String code, String name){
        this.code = code;
        this.name = name;
    }

    public static ConstantDto of(String name, String code){
        return ConstantDto.builder()
                .name(name)
                .code(code)
                .build();
    }

}
