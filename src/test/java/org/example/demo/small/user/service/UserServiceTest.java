package org.example.demo.small.user.service;


import org.example.demo.exception.domain.CommonException;
import org.example.demo.small.mock.FakePasswordEncoder;
import org.example.demo.small.mock.FakeUserRepository;
import org.example.demo.small.mock.TestDateHolder;
import org.example.demo.user.domain.User;
import org.example.demo.user.domain.UserCreate;
import org.example.demo.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {

    private UserService userService;
    private final String NOW = "20250619000001";

    @BeforeEach
    public void init() {
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        FakePasswordEncoder fakePasswordEncoder = new FakePasswordEncoder();
        userService = userService.builder()
                .userRepository(fakeUserRepository)
                .dateHolder(new TestDateHolder(NOW))
                .passwordEncoder(fakePasswordEncoder)
                .build();

        User user1 = User.builder()
                .id(1L)
                .username("tester01")
                .password(fakePasswordEncoder.encode("testerpw1"))
                .createAt("20250604000001")
                .build();
        User user2 = User.builder()
                .id(2L)
                .username("tester02")
                .password(fakePasswordEncoder.encode("testerpw2"))
                .createAt("20250604000002")
                .build();
        fakeUserRepository.save(user1);
        fakeUserRepository.save(user2);
    }

    @Test
    void 기존에_없는_user는_가입이_가능하다() throws Exception {
        //given
        UserCreate userCreate = UserCreate.builder()
                .username("tester03")
                .password("testpw3")
                .build();

        //when
        User save = userService.save(userCreate);
        User result = userService.getById(save.getId());

        //then
        assertThat(result).isNotNull();
    }

    @Test
    void 기존의_username과_동일한_회원은_가입을할_수_없다() throws Exception {
        //given
        UserCreate userCreate = UserCreate.builder()
                .username("tester01")
                .password("testpw3")
                .build();

        //when
        //then
        assertThatThrownBy(() -> userService.save(userCreate))
                .isInstanceOf(CommonException.class);
    }

    @Test
    void 회원_비밀번호는_암호화하여_저장한다() throws Exception {
        //given
        UserCreate userCreate = UserCreate.builder()
                .username("tester03")
                .password("testpw3")
                .build();

        //when
        User save = userService.save(userCreate);
        User result = userService.getById(save.getId());

        //then
        assertThat(result.getPassword()).isEqualTo("encoded:testpw3");
    }

    @Test
    void 회원Id로_회원을_조회할_수_있다() throws Exception {
        //given
        //when
        User result = userService.getById(1L);

        //then
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getUsername()).isEqualTo("tester01");
        assertThat(result.getPassword()).isEqualTo("encoded:testerpw1");
        assertThat(result.getCreateAt()).isEqualTo("20250604000001");
    }

    @Test
    void 회원Id로_조회되지_않으면_에러를_던진다() throws Exception {
        //given
        //when
        //then
        assertThatThrownBy(() -> userService.getById(1234L))
                .isInstanceOf(CommonException.class);
    }
}