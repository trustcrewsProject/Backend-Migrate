package com.example.demo.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum OAuthProvider {
    KAKAO("kakao");

    private final String oAuthProviderName;

    public static final OAuthProvider findOAuthProvider(String oAuthProviderName) {
        return Arrays.stream(OAuthProvider.values())
                .filter(provider -> oAuthProviderName.toLowerCase().equals(provider.getOAuthProviderName()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("해당 OAuth2 제공자가 존재하지 않습니다."));
    }
}
