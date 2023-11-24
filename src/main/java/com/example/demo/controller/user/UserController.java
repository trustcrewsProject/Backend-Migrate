package com.example.demo.controller.user;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 이메일 중복확인 요청
    @GetMapping("/api/user/check-email/{email}")
    public ResponseEntity<ResponseDto<?>> checkEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.checkEmail(email));
    }
}
