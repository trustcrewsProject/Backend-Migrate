package com.example.demo.security.custom;

import com.example.demo.constant.UserStatus;
import com.example.demo.global.exception.customexception.UserCustomException;
import com.example.demo.global.exception.errorcode.UserErrorCode;
import com.example.demo.model.user.User;
import com.example.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        // email, password 로그인이므로 email 값으로 회원 조회
        User user =
                userRepository
                        .findByEmail(username)
                        .orElse(null);

        if(user == null || user.getStatus().equals(UserStatus.DELETED)) {
            throw UserCustomException.NOT_FOUND_USER;
        }

        return new PrincipalDetails(user);
    }
}
