package com.example.demo.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Component
public class JsonWebTokenProvider {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";
    public static final String BEARER_PREFIX = "Bearer ";

    // public static final long ACCESS_TOKEN_EXPIRE_MILLIS_TEST = 1000 * 60; // 1분
    public static final long ACCESS_TOKEN_EXPIRE_MILLIS = 1000 * 60 * 30; // 30분

    // public static final long REFRESH_TOKEN_EXPIRE_MILLIS_TEST = 1000 * 60 * 2; // 2분
    public static final long REFRESH_TOKEN_EXPIRE_MILLIS = 1000 * 60 * 60 * 24 * 7; // 7일

    @Value("${jwt.secret}")
    private String secretKey;

    private Key key;

    // key 초기화
    @PostConstruct
    public void init() {
        String base64EncodedSecretKey = encodedBase64SecretKey(this.secretKey);
        this.key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);
    }

    // JWT Secret Key Base64 Encoded
    private String encodedBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // Base64로 인코드된 Secret Key Decoded -> key 셋팅
    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64URL.decode(base64EncodedSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 토큰 발급 (Access Token & Refresh Token 함께 발급)
    public JsonWebTokenDto generateToken(UserDetails userDetails) {

        // Access Token 생성
        Date accessTokenExpiresIn = getTokenExpiration(ACCESS_TOKEN_EXPIRE_MILLIS);

        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        claims.put("role", userDetails.getAuthorities());

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(accessTokenExpiresIn)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        // Refresh Token 생성
        Date refreshTokenExpiresIn = getTokenExpiration(REFRESH_TOKEN_EXPIRE_MILLIS);

        String refreshToken = Jwts.builder()
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(refreshTokenExpiresIn)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();

        return JsonWebTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // 토큰 만료 시간 설정
    private Date getTokenExpiration(long expirationMillisecond) {
        Date date = new Date();
        return new Date(date.getTime() + expirationMillisecond);
    }
}
