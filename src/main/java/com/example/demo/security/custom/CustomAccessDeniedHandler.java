package com.example.demo.security.custom;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.global.exception.errorcode.UserErrorCode;
import com.example.demo.security.SecurityResponseHandler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

/** 시큐리티 필터단에서 접근 요청한 회원의 권한이 없거나 부족한 경우 예외를 처리하는 핸들러 */
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final UserErrorCode ACCESS_DENIED = UserErrorCode.ACCESS_DENIED;
    private final SecurityResponseHandler securityResponseHandler;

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        securityResponseHandler.sendResponse(
                response, ACCESS_DENIED.getStatus(), ResponseDto.fail(ACCESS_DENIED.getMessage()));
    }
}
