package com.example.demo.security.oauth2.service;

import com.example.demo.constant.OAuthProvider;
import com.example.demo.constant.UserStatus;
import com.example.demo.model.user.User;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.security.oauth2.user.OAuth2UserInfo;
import com.example.demo.security.oauth2.user.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2PrincipalService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String oAuth2ProviderId = oAuth2User.getName();
        log.info("OAuth 제공자: {}", registrationId);
        log.info("OAuth 고유번호: {}", oAuth2ProviderId);

        User user = userRepository
                .findByProviderAndProviderId(OAuthProvider.findOAuthProvider(registrationId), oAuth2ProviderId)
                .orElse(null);

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
                registrationId,
                oAuth2ProviderId);

        return processOAuth2User(user, oAuth2UserInfo);
    }

    private OAuth2User processOAuth2User(User user, OAuth2UserInfo oAuth2UserInfo) {
        if(user == null || user.getStatus().equals(UserStatus.DELETED)) {
            return new OAuth2PrincipalDetails(oAuth2UserInfo);
        }

        return new OAuth2PrincipalDetails(user.getId(), user.getRole(), oAuth2UserInfo);
    }
}
