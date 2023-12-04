package com.example.demo.security.custom;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.user.response.UserLoginSuccessResponseDto;
import com.example.demo.security.jwt.JsonWebTokenDto;
import com.example.demo.security.jwt.JsonWebTokenProvider;
import com.example.demo.service.token.RefreshTokenRedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  UserAuthenticationFilter 에서 사용자 인증 성공 시 실행되는 핸들러
 *  인증 성공 시, AccessToken 을 응답 헤더의 Authorization 헤더에 담고 RefreshToken 을 응답 헤더의 Cookie 에 담는다.
 *  또한, 로그인한 사용자의 정보를 응답 바디에 담아서 함께 응답한다.
 */
@Slf4j
@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JsonWebTokenProvider jsonWebTokenProvider;
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 사용자 인증 객체에서 사용자 정보 가져오기, PrincipalDetails 타입으로 변환
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        // JWT 발급
        JsonWebTokenDto tokens = jsonWebTokenProvider.generateToken(principalDetails);

        // 응답 헤더에 토큰 셋팅
        response.setHeader(JsonWebTokenProvider.AUTHORIZATION_HEADER, "Bearer " + tokens.getAccessToken());
        response.addCookie(createRefreshTokenCookie(tokens.getRefreshToken()));

        // Redis RefreshToken 저장
        refreshTokenRedisService.save(principalDetails.getId(), tokens.getRefreshToken());

        // 로그인 성공 응답
        setSuccessLoginResponse(response, principalDetails);
    }

    // RefreshToken 쿠키 생성
    private Cookie createRefreshTokenCookie(String refreshToken) {
        Cookie cookie = new Cookie("Refresh", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }

    // 로그인 성공 응답
    private void setSuccessLoginResponse(HttpServletResponse response, PrincipalDetails principalDetails) throws IOException {
        // UserLoginSuccessResponseDto 셋팅
        UserLoginSuccessResponseDto loginSuccessResponseDto =
                UserLoginSuccessResponseDto.builder()
                        .userId(principalDetails.getId())
                        .email(principalDetails.getEmail())
                        .nickname(principalDetails.getNickname())
                        .profileImgSrc(principalDetails.getProfileImgSrc())
                        .build();

        // ResponseDto<> 타입 응답 셋팅
        ResponseDto<UserLoginSuccessResponseDto> successResponse =
                ResponseDto.success("로그인에 성공하였습니다.", loginSuccessResponseDto);

        // 로그인 성공 ResponseEntity 응답 생성 및 반환
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(successResponse));
    }
}
