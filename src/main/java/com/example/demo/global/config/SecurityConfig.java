package com.example.demo.global.config;

import com.example.demo.security.custom.UserAuthenticationFilter;
import com.example.demo.security.jwt.JsonWebTokenAuthorizationFilter;
import com.example.demo.security.jwt.JsonWebTokenProvider;
import com.example.demo.service.token.RefreshTokenRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JsonWebTokenProvider jsonWebTokenProvider;
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer configure() throws Exception {
        return (web) -> web.ignoring().antMatchers();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf()
                .disable()
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .disable()
                .httpBasic()
                .disable()
                .apply(new UserAuthenticationFilterConfigurer())
                .and()
                .addFilterBefore(
                        new JsonWebTokenAuthorizationFilter(jsonWebTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // 로그인 요청을 담당하는 필터를 관리하는 클래스
    public class UserAuthenticationFilterConfigurer
            extends AbstractHttpConfigurer<UserAuthenticationFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = authenticationManagerBean();
            UserAuthenticationFilter userAuthenticationFilter =
                    new UserAuthenticationFilter(
                            authenticationManager, jsonWebTokenProvider, refreshTokenRedisService);

            // 해당 필터가 동작할 URL 설정
            userAuthenticationFilter.setFilterProcessesUrl("/api/user/login");

            http.addFilter(userAuthenticationFilter);
        }
    }
}
