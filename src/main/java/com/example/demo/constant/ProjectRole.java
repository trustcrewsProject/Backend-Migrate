package com.example.demo.constant;

import com.example.demo.global.exception.customexception.ProjectMemberAuthCustomException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ProjectRole {

    MANAGER(1L, "매니저"),
    SUB_MANAGER(2L, "부매니저"),
    MEMBER(3L, "구성원"),

    BEING_VOTED(4L, "강제탈퇴투표대상");

    private Long id;
    private String name;

    public boolean isManager() {
        return this.equals(MANAGER);
    }

    public static ProjectRole findProjectRole(Long roleId) {
        return Arrays.stream(ProjectRole.values())
                .filter(role -> role.id.equals(roleId))
                .findFirst()
                .orElseThrow(() -> ProjectMemberAuthCustomException.NOT_FOUND_PROJECT_MEMBER_AUTH);
    }
}
