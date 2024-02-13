package com.example.demo.security.oauth2.user;

import com.example.demo.constant.OAuthProvider;

import java.util.Map;

/**
 * OAuth2 사용자 정보 데이터 구조,
 * 각 제공자 별 사용자 정보 데이터가 다름
 */
public interface OAuth2UserInfo {

    OAuthProvider getProvider();

    String getProviderId();
}
