package org.example.demo.image.service;

import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.example.demo.common.exception.domain.CommonException;
import org.example.demo.common.exception.domain.ErrorCode;
import org.example.demo.common.time.port.DateHolder;
import org.example.demo.common.util.port.UuidHolder;
import org.example.demo.image.domain.Image;
import org.example.demo.image.service.port.ImageManager;
import org.example.demo.image.service.port.ImageMetaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Builder
@RequiredArgsConstructor
@Service
public class ImageService {

    private final UuidHolder uuidHolder;
    private final ImageManager imageManager;
    private final ImageMetaRepository imageMetaRepository;
    private final DateHolder dateHolder;

    @Transactional
    public Image upload(MultipartFile multipartFile, Image image) {
        try {
            Image saveImage = image.readyForUpload(dateHolder, uuidHolder);
            String imageUrl = imageManager.upload(multipartFile, saveImage);

            return imageMetaRepository.save(saveImage)
                    .setFileUrlAfterUpload(imageUrl);
        } catch (IOException e) {
            throw new CommonException(ErrorCode.EXTERNAL_TRX_FAIL, e);
        }
    }
}
