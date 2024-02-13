package com.example.demo.dto.oauth2.response;

import com.example.demo.constant.OAuthProvider;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuth2InfoResponseDto {

    private OAuthProvider provider;
    private String providerId;

    public static OAuth2InfoResponseDto of(OAuthProvider provider, String providerId) {
        return OAuth2InfoResponseDto.builder()
                .provider(provider)
                .providerId(providerId)
                .build();
    }
}
