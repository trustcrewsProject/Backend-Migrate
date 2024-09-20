package com.example.demo.constant;

import com.example.demo.global.common.Constant;
import com.example.demo.global.exception.customexception.CommonCustomException;
import java.util.Arrays;
import lombok.AllArgsConstructor;

/** 마일스톤, 업무의 진행 상태를 관리하는 enum */
@AllArgsConstructor
public enum ProgressStatus implements Constant {
    PS001("PS001", "시작전"),
    PS002("PS002", "진행중"),
    PS003("PS003", "완료"),
    PS004("PS004", "만료");

    private final String code;
    private final String name;

    // 요청한 코드번호의 Progress Status 조회
    public static final ProgressStatus getProgressStatusByCode(String code) {
        return Arrays.stream(ProgressStatus.values())
                .filter(progressStatus -> progressStatus.getCode().equals(code))
                .findFirst()
                .orElseThrow(() -> CommonCustomException.INVALID_INPUT_VALUE);
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
