package com.example.demo.security.jwt;

import com.example.demo.dto.user.request.UserLoginRequestDto;
import com.example.demo.global.exception.customexception.CommonCustomException;
import com.example.demo.global.exception.customexception.UserCustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 인증 필터
// 인증에 성공하면 JWT 발급
@RequiredArgsConstructor
@Slf4j
public class JsonWebTokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // 로그인을 요청했을 때 실행되는 로직
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            UserLoginRequestDto loginRequest = objectMapper.readValue(request.getInputStream(), UserLoginRequestDto.class);

            // 토큰생성
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
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
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, filterChain, authResult);
    }
}
