package com.example.demo.security.oauth2.service;

import com.example.demo.constant.OAuthProvider;
import com.example.demo.constant.Role;
import com.example.demo.security.oauth2.user.OAuth2UserInfo;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
public class OAuth2PrincipalDetails implements OAuth2User, UserDetails {

    private final Long id;
    private final Role role;
    private final OAuth2UserInfo userInfo;

    public OAuth2PrincipalDetails(Long id, Role role, OAuth2UserInfo userInfo) {
        this.id = id;
        this.role = role;
        this.userInfo = userInfo;
    }

    public OAuth2PrincipalDetails(OAuth2UserInfo userInfo) {
        this.id = null;
        this.role = null;
        this.userInfo = userInfo;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return String.valueOf(this.id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + this.role));
    }

    @Override
    public String getName() {
        return null;
    }

    public String getOAuth2ProviderId() { return this.userInfo.getProviderId(); }

    public OAuthProvider getOAuth2Provider() {
        return this.userInfo.getProvider();
    }
}
