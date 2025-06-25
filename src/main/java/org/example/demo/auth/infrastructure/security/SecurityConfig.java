package org.example.demo.auth.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.example.demo.auth.service.port.JwtManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtManager jwtManager;
    private final JwtExtractor jwtExtractor;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationRequest ->
                        authorizationRequest
                                .requestMatchers("/h2-console/**")
                                .permitAll()
                                .requestMatchers(
                                        "/api/auth/**", "/api/users/signup"
                                ).permitAll()
                                .requestMatchers(
                                        HttpMethod.GET
                                ).permitAll()
                                .anyRequest().authenticated())
                .headers(headersConfigurer ->
                        headersConfigurer
                                .frameOptions(
                                        HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                )
                                .contentSecurityPolicy( policyConfig ->
                                        policyConfig.policyDirectives(
                                                "script-src 'self'; " + "img-src 'self'; " +
                                                        "default-src 'self'; " + "frame-src 'self'"
                                        )
                                )
                )
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtManager, jwtExtractor), UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CurrentUserArgumentResolver());
    }
}
