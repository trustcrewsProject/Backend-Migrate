package com.example.demo.dto.projectmember;

public interface ProjectMemberAuthInterface {
    String getCode();
    String getName();
    boolean isMilestoneChangeYN();
    boolean isWorkChangeYN();
    boolean isConfigYn();
}
