package org.example.demo.small.auth.infrastructure.jwt;


import org.example.demo.auth.infrastructure.jwt.JwtManagerImpl;
import org.example.demo.auth.infrastructure.jwt.JwtProperties;
import org.example.demo.auth.service.port.TokenPayload;
import org.example.demo.small.mock.TestDateHolder;
import org.example.demo.user.domain.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JwtManagerImplTest {

    private JwtManagerImpl jwtManager;
    private TestDateHolder testDateHolder;

    @BeforeEach
    public void init() {
        testDateHolder = new TestDateHolder("20250621000001");
        jwtManager = JwtManagerImpl.builder()
                .jwtProperties(new JwtProperties("bXktdmVyeS1zdHJvbmctand0LXNlY3JldC1rZXktdGhhdC1pcy1sb25nLWVub3VnaA=="))
                .dateHolder(testDateHolder)
                .build();
    }

    @Test
    void 만료된_토큰을_검증을_통과하지_못한다() throws Exception {
        //given
        String token = jwtManager.createToken(1L, "tester", List.of(UserRole.ROLE_MEMBER));

        //when
        //then
        boolean result = jwtManager.validateToken(token);
        assertThat(result).isFalse();
    }

    @Test
    void 토큰에서_user정보를_가져올_수_있다() throws Exception {
        //given
        testDateHolder.setUseRealTimeInOneTime();
        String token = jwtManager.createToken(1L, "tester", List.of(UserRole.ROLE_MEMBER));

        //when
        TokenPayload tokenPayload = jwtManager.parseClaims(token);

        //then
        assertThat(tokenPayload.getId()).isEqualTo(1L);
        assertThat(tokenPayload.getUsename()).isEqualTo("tester");
        assertThat(tokenPayload.getRoles()).isEqualTo(List.of(UserRole.ROLE_MEMBER.name()));
    }
}