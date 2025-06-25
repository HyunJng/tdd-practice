package org.example.demo.small.auth.service;


import org.example.demo.auth.domain.Login;
import org.example.demo.auth.service.AuthService;
import org.example.demo.auth.service.port.JwtManager;
import org.example.demo.common.exception.domain.CommonException;
import org.example.demo.common.exception.domain.ErrorCode;
import org.example.demo.small.mock.FakeJwtManager;
import org.example.demo.small.mock.FakePasswordEncoder;
import org.example.demo.small.mock.FakeUserRepository;
import org.example.demo.small.mock.TestDateHolder;
import org.example.demo.user.domain.User;
import org.example.demo.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AuthServiceTest {

    private UserService userService;
    private AuthService authService;
    private JwtManager jwtManager;
    private TestDateHolder testDateHolder;

    private final String NOW = "20250620000001";

    @BeforeEach
    public void init() {
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        FakePasswordEncoder fakePasswordEncoder = new FakePasswordEncoder();
        testDateHolder = new TestDateHolder(NOW);

        userService = userService.builder()
                .userRepository(fakeUserRepository)
                .dateHolder(testDateHolder)
                .passwordEncoder(fakePasswordEncoder)
                .build();
        jwtManager = new FakeJwtManager();
        authService = AuthService.builder()
                .passwordEncoder(new FakePasswordEncoder())
                .userRepository(fakeUserRepository)
                .jwtManager(jwtManager)
                .build();

        User user1 = User.builder()
                .id(1L)
                .username("tester01")
                .password(fakePasswordEncoder.encode("testerpw1"))
                .createAt("20250604000001")
                .build();
        fakeUserRepository.save(user1);
    }

    @Test
    void 회원이_아니면_로그인에_실패한다() throws Exception {
        //given
        Login login = Login.builder()
                .username("imnotuser")
                .password("1234")
                .build();
        //when
        //then
        assertThatThrownBy(() -> authService.login(login))
                .isInstanceOf(CommonException.class)
                .hasMessageContaining(ErrorCode.RESOURCE_NOT_FOUND.getMessage("user"));
    }

    @Test
    void 비밀번호가_틀리면_로그인에_실패한다() throws Exception {
        //given
        Login login = Login.builder()
                .username("tester01")
                .password("1234")
                .build();
        //when
        //then
        assertThatThrownBy(() -> authService.login(login))
                .isInstanceOf(CommonException.class)
                .hasMessageContaining(ErrorCode.WRONG_REQUEST_PARAM_DATA.getMessage("password"));
    }

    @Test
    void 로그인에_성공하면_유효한_jwt_토큰을_반환한다() throws Exception {
        //given
        testDateHolder.setUseRealTimeInOneTime();
        Login login = Login.builder()
                .username("tester01")
                .password("testerpw1")
                .build();
        //when
        String loginToken = authService.login(login);
        boolean result = jwtManager.validateToken(loginToken);

        //then
        assertThat(result).isTrue();
    }
}