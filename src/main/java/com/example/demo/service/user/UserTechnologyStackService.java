package com.example.demo.service.user;

import com.example.demo.model.user.UserTechnologyStack;

import java.util.List;

public interface UserTechnologyStackService {

    // 회원 기술스택 리스트 저장
    List<UserTechnologyStack> saveAll(List<UserTechnologyStack> technologyStackList);
}
