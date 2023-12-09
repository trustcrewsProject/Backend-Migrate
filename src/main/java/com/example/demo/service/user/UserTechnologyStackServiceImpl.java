package com.example.demo.service.user;

import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.model.user.User;
import com.example.demo.model.user.UserTechnologyStack;
import com.example.demo.repository.user.UserTechnologyStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserTechnologyStackServiceImpl implements UserTechnologyStackService{

    private final UserTechnologyStackRepository userTechnologyStackRepository;


    @Override
    public List<UserTechnologyStack> saveAll(List<UserTechnologyStack> technologyStackList) {
        return userTechnologyStackRepository.saveAll(technologyStackList);
    }

    // 회원 기술스택 목록 생성
    private List<UserTechnologyStack> createUserTechnologyStackList(User user, List<TechnologyStack> techStackList) {
        return techStackList.stream()
                .map(tech -> createUserTechnologyStack(user, tech))
                .collect(Collectors.toList());
    }

    // 회원 기술스택 엔티티 생성
    private UserTechnologyStack createUserTechnologyStack(User user, TechnologyStack technologyStack) {
        return UserTechnologyStack.builder()
                .user(user)
                .technologyStack(technologyStack)
                .build();
    }

    @Override
    public void deleteUserTechnologyStackList(List<UserTechnologyStack> technologyStackList) {
        userTechnologyStackRepository.deleteAll(technologyStackList);
    }
}
