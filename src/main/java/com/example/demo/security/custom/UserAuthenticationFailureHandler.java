package com.example.demo.security.custom;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.global.exception.errorcode.UserErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  UserAuthenticationFilter 에서 사용자 인증 실패 시 실행되는 핸들러
 *  UsernameNotFoundException OR BadCredentialsException 발생 시 커스텀 예외를 응답
 */
@Slf4j
public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    // 회원 인증 실패 시 응답 할 커스텀 ErrorCode
    private static final UserErrorCode INVALID_AUTHENTICATION = UserErrorCode.INVALID_AUTHENTICATION;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        // ResponseDto<> 타입 응답 셋팅
        ResponseDto<Void> failureResponse = ResponseDto.fail(INVALID_AUTHENTICATION.getMessage());


    }

}
