package org.example.demo.medium.image.infrastructure.aws;

import org.example.demo.image.domain.Image;
import org.example.demo.image.infrastructure.aws.AwsImageManager;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AwsImageManagerTest {

    @Autowired
    private AwsImageManager awsImageManager;

//    @Test
    void 이미지를_aws_의_s3에_올릴_수_있다() throws Exception {
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

        Image image = Image.builder()
                .filename(multipartFile.getOriginalFilename())
                .build();

        // when
        // then
        Assertions.assertDoesNotThrow(() -> {
            awsImageManager.upload(multipartFile, image);
        });
    }

//    @Test
    void aws의_s3에서_이미지를_삭제할_수_있다() throws Exception {
        //given
        //when
        //then
        Assertions.assertDoesNotThrow(() -> {
            awsImageManager.delete("0cb96b17-6030-41b3-97dc-6674924ffa90_car.jpg");
        });
    }
}