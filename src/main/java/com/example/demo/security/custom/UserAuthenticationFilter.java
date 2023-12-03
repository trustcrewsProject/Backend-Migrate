package com.example.demo.security.custom;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.user.request.UserLoginRequestDto;
import com.example.demo.dto.user.response.UserLoginSuccessResponseDto;
import com.example.demo.global.exception.customexception.CommonCustomException;
import com.example.demo.global.exception.customexception.UserCustomException;
import com.example.demo.security.jwt.JsonWebTokenDto;
import com.example.demo.security.jwt.JsonWebTokenProvider;
import com.example.demo.service.token.RefreshTokenRedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 인증 필터
// 인증에 성공하면 JWT 발급
@RequiredArgsConstructor
@Slf4j
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JsonWebTokenProvider jsonWebTokenProvider;
    private final RefreshTokenRedisService refreshTokenRedisService;

    // 로그인을 요청했을 때 실행되는 로직
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserLoginRequestDto loginRequest =
                    objectMapper.readValue(request.getInputStream(), UserLoginRequestDto.class);

            // 토큰생성
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword());
            Authentication authentication = authenticationManager.authenticate(token);

            return authentication;
        } catch (IOException | NullPointerException e) {
            log.error(e.getMessage());
            throw CommonCustomException.INTERNAL_SERVER_ERROR;
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            throw UserCustomException.INVALID_AUTHENTICATION;
        }
    }

    // 인증에 성공했을 경우 실행되는 로직
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication authResult)
            throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        // 토큰 발급
        JsonWebTokenDto tokens = jsonWebTokenProvider.generateToken(principalDetails);

        // 응답헤더에 토큰 셋팅
        response.setHeader("Authorization", "Bearer " + tokens.getAccessToken());
        response.addCookie(createRefreshTokenCookie(tokens.getRefreshToken()));

        // Redis RefreshToken 저장
        refreshTokenRedisService.save(principalDetails.getId(), tokens.getRefreshToken());

        // UserLoginSuccessResponseDto 셋팅
        UserLoginSuccessResponseDto loginSuccessResponse =
                UserLoginSuccessResponseDto.builder()
                        .userId(principalDetails.getId())
                        .email(principalDetails.getEmail())
                        .nickname(principalDetails.getNickname())
                        .profileImgSrc(principalDetails.getProfileImgSrc())
                        .build();

        // ResponseDto<> 형으로 변환
        ResponseDto<UserLoginSuccessResponseDto> responseDto =
                ResponseDto.success("로그인에 성공하였습니다.", loginSuccessResponse);

        // ResponseEntity 응답 생성 및 반환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseDto);
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(jsonResponse);
    }

    // RefreshToken 쿠키 생성
    private Cookie createRefreshTokenCookie(String refreshToken) {
        Cookie cookie = new Cookie("Refresh", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }
}
