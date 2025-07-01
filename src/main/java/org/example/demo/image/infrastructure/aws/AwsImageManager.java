package org.example.demo.image.infrastructure.aws;

import lombok.RequiredArgsConstructor;
import org.example.demo.image.domain.Image;
import org.example.demo.image.service.port.ImageManager;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class AwsImageManager implements ImageManager {

    private final S3Client s3Client;
    private final S3Properties s3Properties;
    private final CloudFrontProperties cloudFrontProperties;

    @Override
    public String upload(MultipartFile file, Image image) throws IOException {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(s3Properties.getBucket())
                        .key(image.getFilename())
                        .contentType(file.getContentType())
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );

        return getCloudfrontImageUrl(image.getFilename());
    }

    private String getCloudfrontImageUrl(String fileName) {
        return cloudFrontProperties.getDomain() + fileName;
    }

    @Override
    public void delete(String filename) {
        s3Client.deleteObject(DeleteObjectRequest.builder()
                .bucket(s3Properties.getBucket())
                .key(filename)
                .build());
    }
}