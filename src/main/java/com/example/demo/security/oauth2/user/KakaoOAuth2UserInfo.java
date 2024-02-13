package com.example.demo.security.oauth2.user;

import com.example.demo.constant.OAuthProvider;

/**
 * 카카오 사용자 정보
 */
public class KakaoOAuth2UserInfo implements OAuth2UserInfo{
    private final String kakaoProviderId;

    public KakaoOAuth2UserInfo(String oAuthProviderId) {
        this.kakaoProviderId = oAuthProviderId;
    }

    @Override
    public OAuthProvider getProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String getProviderId() {
        return this.kakaoProviderId;
    }
}
