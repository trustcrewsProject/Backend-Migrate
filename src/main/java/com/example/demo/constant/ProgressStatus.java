package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 마일스톤, 업무의 진행 상태를 관리하는 enum
 */
@AllArgsConstructor
@Getter
public enum ProgressStatus {
    BEFORE_START("PS001", "시작전"),
    ON_GOING("PS002", "진행중"),
    COMPLETION("PS003", "완료"),
    EXPIRED("PS004", "만료");

    private String code;
    private String description;
}
