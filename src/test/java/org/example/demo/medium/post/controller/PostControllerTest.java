package org.example.demo.medium.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.demo.auth.service.port.JwtManager;
import org.example.demo.common.exception.domain.ErrorCode;
import org.example.demo.post.controller.dto.PostChange;
import org.example.demo.post.controller.dto.PostSave;
import org.example.demo.user.domain.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ActiveProfiles("prod")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup(value = {
        @Sql(value = "/sql/post-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtManager jwtManager;

    @Test
    void 게시글_목록을_조회할_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/posts")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.pageable.length()").exists());
//                .andDo(print());
    }

    @Test
    void 게시글_목록_조회_시_page_와_size_를_숫자로_전달하지_않으면_오류를_던진다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/posts")
                        .param("page", "string")
                        .param("size", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(ErrorCode.BAD_REQUEST_PARAM_FORMAT.getMessage() + ": page"));
//                .andDo(print());
    }

    @Test
    void 게시글_상세_조회를_할_수_있다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").isString())
                .andExpect(jsonPath("$.content").isString())
                .andExpect(jsonPath("$.writerId").isNumber())
                .andExpect(jsonPath("$.writer").isString())
                .andExpect(jsonPath("$.createAt").isString());
//                .andDo(print());
    }

    @Test
    void 게시글_상세_조회_시_존재하지_않는_게시글이면_오류를_던진다() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/api/posts/{id}", 123456))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("존재하지않는 Post 입니다"));
//                .andDo(print());
    }

    @Test
    void 회원은_게시글을_생성할_수_있다() throws Exception {
        //given
        PostSave.Request request = new PostSave.Request();
        request.setTitle("테스트타이틀");
        request.setContent("게시글 생성 테스트입니다.");
        String token = jwtManager.createToken(1L, "testuser1", List.of(UserRole.ROLE_MEMBER));

        String requestStr = new ObjectMapper().writeValueAsString(request);

        //when
        //then
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(requestStr))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("테스트타이틀"))
                .andExpect(jsonPath("$.content").value("게시글 생성 테스트입니다."))
                .andExpect(jsonPath("$.writerId").value(1))
                .andExpect(jsonPath("$.writer").isString())
                .andExpect(jsonPath("$.createAt").isString());
//                .andDo(print());
    }

    @Test
    void 회원이_아닌_자는_게시글을_생성할_수_없다() throws Exception {
        //given
        PostSave.Request request = new PostSave.Request();
        request.setTitle("테스트타이틀");
        request.setContent("게시글 생성 테스트입니다.");
        String token = jwtManager.createToken(123456, "testuser1", List.of(UserRole.ROLE_MEMBER));

        String requestStr = new ObjectMapper().writeValueAsString(request);

        //when
        //then
        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(requestStr))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("존재하지않는 USER 입니다"));
//                .andDo(print());
    }

    @Test
    void 게시글_작성자는_게시글을_수정할_수_있다() throws Exception {
        //given
        PostChange.Request request = new PostChange.Request();
        request.setTitle("수정 타이틀");
        request.setContent("수정 테스트 중입니다");
        String token = jwtManager.createToken(1L, "testuser1", List.of(UserRole.ROLE_MEMBER));

        String requestStr = new ObjectMapper().writeValueAsString(request);

        //when
        //then
        mockMvc.perform(put("/api/posts/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(requestStr))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").isString())
                .andExpect(jsonPath("$.content").isString())
                .andExpect(jsonPath("$.writerId").isNumber())
                .andExpect(jsonPath("$.writer").isString())
                .andExpect(jsonPath("$.createAt").isString())
                .andExpect(jsonPath("$.modifiedAt").isString());
//                .andDo(print());
    }

    @Test
    void 게시글_작성자는_게시글을_삭제할_수_있다() throws Exception {
        //given
        String token = jwtManager.createToken(1L, "testuser1", List.of(UserRole.ROLE_MEMBER));
        //when
        //then
        mockMvc.perform(delete("/api/posts/{id}", 1)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }
}