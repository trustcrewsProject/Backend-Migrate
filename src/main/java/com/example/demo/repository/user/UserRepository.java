package com.example.demo.repository.user;

import com.example.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일 확인
    boolean existsByEmail(String email);

    // 닉네임 확인
    boolean existsByNickname(String nickname);

    // 이메일로 회원 조화
    Optional<User> findByEmail(String email);
}
