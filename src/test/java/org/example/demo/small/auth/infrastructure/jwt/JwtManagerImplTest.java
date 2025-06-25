package org.example.demo.small.auth.infrastructure.jwt;


import org.example.demo.auth.infrastructure.jwt.JwtManagerImpl;
import org.example.demo.auth.infrastructure.jwt.JwtProperties;
import org.example.demo.small.mock.TestDateHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        String token = jwtManager.createToken(1L);

        //when
        //then
        boolean result = jwtManager.validateToken(token);
        assertThat(result).isFalse();
    }

    @Test
    void 토큰에서_userId를_가져올_수_있다() throws Exception {
        //given
        testDateHolder.setUseRealTimeInOneTime();
        String token = jwtManager.createToken(1L);

        //when
        long userIdFromToken = jwtManager.getUserIdFromToken(token);

        //then
        assertThat(userIdFromToken).isEqualTo(1L);
    }
}