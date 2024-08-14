package com.example.demo.constant;

import com.example.demo.global.common.Constant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ProjectFWReason implements Constant {
    FWR1001("FWR1001", "맡은 작업 수행에 비협조적인 태도"),
    FWR1002("FWR1002", "팀원에게 불쾌감 및 모욕감을 주는 언어사용"),
    FWR1003("FWR1003", "지원시 사용 기술스택을 허위로 기재"),
    FWR1004("FWR1004", "기타");

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
