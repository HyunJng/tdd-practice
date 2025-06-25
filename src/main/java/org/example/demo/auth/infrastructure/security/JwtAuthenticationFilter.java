package org.example.demo.auth.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.demo.auth.service.port.JwtManager;
import org.example.demo.auth.service.port.TokenPayload;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;
    private final JwtExtractor jwtExtractor;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {
        String token = jwtExtractor.resolveToken(request);
        if (token != null && jwtManager.validateToken(token)) {
            TokenPayload tokenPayload = jwtManager.parseClaims(token);
            UserDetails customUserDetails = new CustomUserDetails(tokenPayload);
            UsernamePasswordAuthenticationToken auth
                    = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }


}
