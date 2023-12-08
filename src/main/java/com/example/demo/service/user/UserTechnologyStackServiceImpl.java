package com.example.demo.service.user;

import com.example.demo.model.user.UserTechnologyStack;
import com.example.demo.repository.user.UserTechnologyStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTechnologyStackServiceImpl implements UserTechnologyStackService{

    private final UserTechnologyStackRepository userTechnologyStackRepository;


    @Override
    public List<UserTechnologyStack> saveAll(List<UserTechnologyStack> technologyStackList) {
        return userTechnologyStackRepository.saveAll(technologyStackList);
    }

    @Override
    public void deleteUserTechnologyStackList(List<UserTechnologyStack> technologyStackList) {
        userTechnologyStackRepository.deleteAll(technologyStackList);
    }
}
