package org.example.demo.medium.auth.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.demo.auth.controller.dto.UserLoginDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ActiveProfiles("prod")
@SpringBootTest
@AutoConfigureMockMvc
@SqlGroup(value = {
        @Sql(value = "/sql/auth-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 로그인을_성공하면_accessToken을_응답받는다() throws Exception {
        //given
        UserLoginDto.Request request = UserLoginDto.Request.builder()
                .username("tester01")
                .password("testpw01")
                .build();

        String requestJson = new ObjectMapper().writeValueAsString(request);
        //when
        //then
        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").isString());
//                .andDo(print());
    }

    @Test
    void 로그인을_실패하면_오류메시지를_응답받는다() throws Exception {
        //given
        UserLoginDto.Request request = UserLoginDto.Request.builder()
                .username("tester01")
                .password("testpw02")
                .build();

        String requestJson = new ObjectMapper().writeValueAsString(request);
        //when
        //then
        mockMvc.perform(post("/api/auth/login")
                        .contentType("application/json")
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").isString());
//                .andDo(print());
    }
}