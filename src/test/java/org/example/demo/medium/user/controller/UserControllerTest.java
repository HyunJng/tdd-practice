package org.example.demo.medium.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.demo.common.exception.ErrorCode;
import org.example.demo.user.controller.dto.SignUp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@SqlGroup(value = {
        @Sql(value = "/sql/user-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        
})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void username은_최소_4글자_이상_10자_이하가_아니면_오류를_발생시킨다() throws Exception {
        //given
        SignUp.Request signup1 = new SignUp.Request();
        signup1.setUsername("IamTester03");
        signup1.setPassword("testpw03");

        SignUp.Request signup2 = new SignUp.Request();
        signup2.setUsername("tes");
        signup2.setPassword("testpw03");

        ObjectMapper objectMapper = new ObjectMapper();
        String request1 = objectMapper.writeValueAsString(signup1);
        String request2 = objectMapper.writeValueAsString(signup2);

        //when
        //then
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST_PARAM_SIZE.getMessage()))
                .andDo(print());

        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request2))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST_PARAM_SIZE.getMessage()))
                .andDo(print());

    }

    @Test
    void username은_알파벳_소문자와_숫자로_구성되지_않으면_오류를_발생시킨다() throws Exception {
        //given
        SignUp.Request signup1 = new SignUp.Request();
        signup1.setUsername("TESTER");
        signup1.setPassword("testpw03");

        SignUp.Request signup2 = new SignUp.Request();
        signup2.setUsername("#tester");
        signup2.setPassword("testpw03");

        ObjectMapper objectMapper = new ObjectMapper();
        String request1 = objectMapper.writeValueAsString(signup1);
        String request2 = objectMapper.writeValueAsString(signup2);

        //when
        //then
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST_PARAM_FORMAT.getMessage()))
                .andDo(print());

        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request2))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST_PARAM_FORMAT.getMessage()))
                .andDo(print());

    }
    @Test
    void password는_최소_8자_이상_15자_이하가_아니면_오류를_발생시킨다() throws Exception {
        //given
        SignUp.Request signup1 = new SignUp.Request();
        signup1.setUsername("tester03");
        signup1.setPassword("test");

        SignUp.Request signup2 = new SignUp.Request();
        signup2.setUsername("tester04");
        signup2.setPassword("testpw03testpw03");

        ObjectMapper objectMapper = new ObjectMapper();
        String request1 = objectMapper.writeValueAsString(signup1);
        String request2 = objectMapper.writeValueAsString(signup2);

        //when
        //then
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST_PARAM_SIZE.getMessage()))
                .andDo(print());

        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request2))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST_PARAM_SIZE.getMessage()))
                .andDo(print());

    }

    @Test
    void password는_알파벳_대소문자와_숫자로_구성되지_않으면_오류를_발생시킨다() throws Exception {
        //given
        SignUp.Request signup1 = new SignUp.Request();
        signup1.setUsername("tester03");
        signup1.setPassword("#!testpw03");

        ObjectMapper objectMapper = new ObjectMapper();
        String request1 = objectMapper.writeValueAsString(signup1);

        //when
        //then
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request1))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST_PARAM_FORMAT.getMessage()))
                .andDo(print());

    }

    @Test
    void 회원_정보를_저장하는데_성공하면_저장된_정보를_반환한다() throws Exception {
        //given
        SignUp.Request signup1 = new SignUp.Request();
        signup1.setUsername("tester03");
        signup1.setPassword("testpw03");

        String request1 = new ObjectMapper().writeValueAsString(signup1);

        //when
        //then
        mockMvc.perform(post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.username").isString())
                .andExpect(jsonPath("$.createAt").isString())
                .andDo(print());
    }
}