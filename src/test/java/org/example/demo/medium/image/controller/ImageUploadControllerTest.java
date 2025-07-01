package org.example.demo.medium.image.controller;

import org.example.demo.auth.service.port.JwtManager;
import org.example.demo.user.domain.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@SqlGroup(value = {
        @Sql(value = "/sql/image-upload-controller-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS),
        @Sql(value = "/sql/delete-all.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class ImageUploadControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtManager jwtManager;

    @Test
    void 이미지를_저장소에_업로드하고_url을_응답받는다() throws Exception {
        //given
        InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("image/car.jpg");

        assertNotNull(inputStream, "이미지 없음");
        byte[] fileBytes = inputStream.readAllBytes();

        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "car.jpg",
                "image/jpg",
                fileBytes
        );

        String token = jwtManager.createToken(1L, "testuser1", List.of(UserRole.ROLE_MEMBER));

        //when
        //then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/images")
                        .file(multipartFile)
                        .header("Authorization", "Bearer " + token)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.filename").isString())
                .andExpect(jsonPath("$.fileUrl").isString())
                .andDo(print());
    }
}