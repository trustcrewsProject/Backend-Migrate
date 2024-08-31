package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CreateLimitCnt {
    TASK("TASK", 100),
    MILESTONE("MILESTONE", 10);

    private String name;
    private int count;
}
