package org.example.demo.small.image.service;

import org.assertj.core.api.Assertions;
import org.example.demo.common.exception.domain.CommonException;
import org.example.demo.common.exception.domain.ErrorCode;
import org.example.demo.image.domain.Image;
import org.example.demo.image.service.ImageService;
import org.example.demo.small.mock.FakeImageMetaRepository;
import org.example.demo.small.mock.FakeImageManager;
import org.example.demo.small.mock.TestDateHolder;
import org.example.demo.small.mock.TestUuidHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;

class ImageServiceTest {

    private ImageService imageService;
    private FakeImageManager imageUploder;
    private FakeImageMetaRepository imageMetaRepository;

    @BeforeEach
    void init() {
        imageUploder = new FakeImageManager("http://test");
        imageMetaRepository = new FakeImageMetaRepository();
        imageService = ImageService.builder()
                .uuidHolder(new TestUuidHolder("123456"))
                .dateHolder(new TestDateHolder("20250702000001"))
                .imageManager(imageUploder)
                .imageMetaRepository(imageMetaRepository)
                .build();
    }

    @Test
    void 이미지를_업로드하면_이미지URL을_반환한다() throws Exception {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("car.jpg", (byte[]) null);
        Image image = Image.builder()
                .filename("car.jpg")
                .build();

        //when
        Image upload = imageService.upload(mockMultipartFile, image);

        //then
        assertThat(upload.getFileUrl()).isEqualTo("http://test/123456_car.jpg");
    }

    @Test
    void 이미지_업로드에_실패하면_오류를_반환한다() throws Exception {
        //given
        imageUploder.startUploadExceptionTest();
        MockMultipartFile mockMultipartFile = new MockMultipartFile("car.jpg", (byte[]) null);
        Image image = Image.builder()
                .filename("car.jpg")
                .build();

        //when
        //then
        Assertions.assertThatThrownBy(() -> imageService.upload(mockMultipartFile, image))
                .isInstanceOf(CommonException.class)
                .hasMessageContaining(ErrorCode.EXTERNAL_TRX_FAIL.getMessage());
    }

    @Test
    void 이미지를_업로드하면_meta_data를_저장한다() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile("car.jpg", (byte[]) null);
        Image image = Image.builder()
                .filename("car.jpg")
                .build();

        //when
        Image upload = imageService.upload(mockMultipartFile, image);

        //then
        assertThat(imageMetaRepository.findById(upload.getId())).isNotNull();
    }

}