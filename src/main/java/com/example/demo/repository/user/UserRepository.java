package com.example.demo.repository.user;

import com.example.demo.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    // 이메일 확인
    boolean existsByEmail(String email);
}
