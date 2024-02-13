package com.example.demo.security.oauth2.handler;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.oauth2.response.OAuth2InfoResponseDto;
import com.example.demo.global.exception.customexception.OAuth2CustomException;
import com.example.demo.global.util.CookieUtils;
import com.example.demo.security.SecurityResponseHandler;
import com.example.demo.security.jwt.JsonWebTokenDto;
import com.example.demo.security.jwt.JsonWebTokenProvider;
import com.example.demo.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.example.demo.security.oauth2.service.OAuth2PrincipalDetails;
import com.example.demo.service.token.RefreshTokenRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final int REFRESH_COOKIE_MAX_AGE = 7 * 24 * 60 * 60;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final JsonWebTokenProvider jsonWebTokenProvider;
    private final SecurityResponseHandler securityResponseHandler;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2PrincipalDetails oAuthPrincipal = getOAuth2UserPrincipal(authentication);
        clearAuthenticationAttributes(request, response);

        if(oAuthPrincipal == null) {
            throw OAuth2CustomException.NOT_FOUND_OAUTH_PRINCIPAL;
        }

        // 가입되지 않은 회원일 경우
        if(oAuthPrincipal.getId() == null) {
            OAuth2InfoResponseDto infoResponse = OAuth2InfoResponseDto.of(oAuthPrincipal.getOAuth2Provider(), oAuthPrincipal.getOAuth2ProviderId());

            securityResponseHandler
                    .sendResponse(response, HttpStatus.OK, ResponseDto.success("가입되지 않은 소셜 회원입니다. 가입을 진행해주세요.", infoResponse));
            return;
        }

        // 토큰 발급
        JsonWebTokenDto tokens = jsonWebTokenProvider.generateToken(oAuthPrincipal);
        refreshTokenRedisService.save(oAuthPrincipal.getId(), tokens.getRefreshToken());

        // 토큰 셋팅
        response.setHeader("Authorization", tokens.getAccessToken());
        CookieUtils.addCookie(response, "Refresh", tokens.getRefreshToken(), REFRESH_COOKIE_MAX_AGE);

        securityResponseHandler.sendResponse(response, HttpStatus.OK, ResponseDto.success("소셜 로그인에 성공하였습니다."));
    }

    private OAuth2PrincipalDetails getOAuth2UserPrincipal(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof OAuth2PrincipalDetails) {
            return (OAuth2PrincipalDetails) principal;
        }
        return null;
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }
}
