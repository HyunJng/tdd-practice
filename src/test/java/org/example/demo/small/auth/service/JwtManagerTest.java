package org.example.demo.small.auth.service;


import org.example.demo.auth.infrastructure.jwt.JwtManager;
import org.example.demo.auth.infrastructure.jwt.JwtProperties;
import org.example.demo.common.exception.domain.CommonException;
import org.example.demo.small.mock.TestDateHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtManagerTest {

    private JwtManager jwtManager;
    private TestDateHolder testDateHolder;

    @BeforeEach
    public void init() {
        testDateHolder = new TestDateHolder("20250621000001");
        jwtManager = JwtManager.builder()
                .jwtProperties(new JwtProperties("bXktdmVyeS1zdHJvbmctand0LXNlY3JldC1rZXktdGhhdC1pcy1sb25nLWVub3VnaA=="))
                .dateHolder(testDateHolder)
                .build();
    }

    @Test
    void 만료된_토큰을_오류를_발생시킨다() throws Exception {
        //given
        String token = jwtManager.createToken(1L);

        //when
        //then
        assertThatThrownBy(() -> jwtManager.validateToken(token))
                .isInstanceOf(CommonException.class);
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