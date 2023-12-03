package com.example.demo.controller.user;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.user.request.UserCreateRequestDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.user.UserFacade;
import com.example.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;
    private final UserService userService;

    // 이메일 중복확인 요청
    @GetMapping("/api/user/check-email/{email}")
    public ResponseEntity<ResponseDto<?>> checkEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkEmail(email));
    }

    // 닉네임 중복확인 요청
    @GetMapping("/api/user/check-nickname/{nickname}")
    public ResponseEntity<ResponseDto<?>> checkNickname(@PathVariable String nickname) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkNickname(nickname));
    }

    // 회원가입
    @PostMapping("/api/user")
    public ResponseEntity<ResponseDto<?>> signup(@Valid @RequestBody UserCreateRequestDto createRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userFacade.createUser(createRequest));
    }

    @GetMapping("/api/user/me")
    public ResponseEntity<ResponseDto<?>> myInfo(@AuthenticationPrincipal PrincipalDetails principal) {
        System.out.println("id : " + principal.getUsername());
        System.out.println("email : " + principal.getEmail());
        System.out.println("nickname : " + principal.getNickname());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
