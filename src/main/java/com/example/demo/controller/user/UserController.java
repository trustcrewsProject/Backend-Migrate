package com.example.demo.controller.user;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.user.request.UserCreateRequestDto;
import com.example.demo.dto.user.request.UserUpdateRequestDto;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.user.UserFacade;
import com.example.demo.service.user.UserService;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;
    private final UserService userService;

    // 이메일 중복확인 요청
    @GetMapping("/check-email/{email}/public")
    public ResponseEntity<ResponseDto<?>> checkEmail(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkEmail(email));
    }

    // 닉네임 중복확인 요청
    @GetMapping("/check-nickname/{nickname}/public")
    public ResponseEntity<ResponseDto<?>> checkNickname(@PathVariable String nickname) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkNickname(nickname));
    }

    // 회원가입
    @PostMapping("/public")
    public ResponseEntity<ResponseDto<?>> signup(
            @Valid @RequestBody UserCreateRequestDto createRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userFacade.createUser(createRequest));
    }

    // 회원수정
    @PutMapping()
    public ResponseEntity<ResponseDto<?>> update(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestPart MultipartFile file,
            @Valid @RequestPart UserUpdateRequestDto updateRequest) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userFacade.updateUser(user, file, updateRequest));
    }

    // 간단한 내 정보 조회
    @GetMapping("/simple-me")
    public ResponseEntity<ResponseDto<?>> simpleMyInfo(
            @AuthenticationPrincipal PrincipalDetails user) {
        return ResponseEntity.status(HttpStatus.OK).body(userFacade.getSimpleMyInfo(user));
    }

    // 내 정보 조회
    @GetMapping("/me")
    public ResponseEntity<ResponseDto<?>> myInfo(@AuthenticationPrincipal PrincipalDetails user) {
        return ResponseEntity.status(HttpStatus.OK).body(userFacade.getMyInfo(user));
    }

    // 내 프로젝트 이력 목록 조회
    @GetMapping("/me/project-history")
    public ResponseEntity<ResponseDto<?>> myProjectHistoryList(
            @AuthenticationPrincipal PrincipalDetails user,
            @RequestParam Optional<Integer> pageNumber) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userFacade.getMyProjectHistoryList(user, pageNumber.orElse(0)));
    }
}
