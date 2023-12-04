package com.example.demo.security.jwt;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.global.exception.customexception.CustomException;
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

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException customException) {
            setExceptionResponse(response, customException.getErrorCode());
        }
    }

    // 예외 응답 셋팅 및 반환
    private void setExceptionResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        String exceptionResponse = objectMapper.writeValueAsString(ResponseDto.fail(errorCode.getMessage()));
        response.setStatus(errorCode.getStatus().value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(exceptionResponse);
    }
}
