package com.example.demo.service.user;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.model.user.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserService {

    // 이메일 중복확인 로직
    @Transactional(readOnly = true)
    ResponseDto<?> checkEmail(String email);

    // 닉네임 중복확인 로직
    @Transactional(readOnly = true)
    ResponseDto<?> checkNickname(String nickname);

    User getUserById(Long userId);
}
