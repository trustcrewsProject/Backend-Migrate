package com.example.demo.global.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * CORS 설정 파일
 */
@Configuration
public class CorsConfig {

    // 로컬 서버 주소 (프로젝트 완료 후 제거)
    private static final String LOCAL_SERVER_ORIGIN = "http://localhost:3000";

    // 백엔드 로컬 서버 주소 (프로젝트 완료 후 제거)
    private static final String LOCAL_BACKEND_SERVER_ORIGIN = "http://localhost:8080";

    // 배포 서버 주소
    private static final String DEPLOYED_SERVER_ORIGIN = "http://3.35.111.141:3000";

    // 허용 HttpMethod 리
    private static final List<String> PERMIT_HTTP_METHOD =
            List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String SET_COOKIE_HEADER = "Set-Cookie";

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(LOCAL_SERVER_ORIGIN);
        configuration.addAllowedOrigin(LOCAL_BACKEND_SERVER_ORIGIN);
        configuration.addAllowedOrigin(DEPLOYED_SERVER_ORIGIN);
        configuration.setAllowedMethods(PERMIT_HTTP_METHOD);
        configuration.addExposedHeader(AUTHORIZATION_HEADER);
        configuration.addExposedHeader(SET_COOKIE_HEADER);
        configuration.addAllowedHeader("*");
        configuration.setMaxAge(3600L);
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
