package com.example.demo.security.jwt;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.global.exception.customexception.CustomException;
import com.example.demo.global.exception.customexception.TokenCustomException;
import com.example.demo.global.exception.errorcode.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  JWT 관련 예외 발생 시, 예외를 처리하는 커스텀 필터
 *  JsonWebTokenAuthenticationFilter 보다 먼저 수행되어 JWT 검증 및 인증 과정 중 발생하는 예외를 처리
 */
@Slf4j
@RequiredArgsConstructor
public class JsonWebTokenExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenCustomException TokenCustomException) {

        }
    }
}
