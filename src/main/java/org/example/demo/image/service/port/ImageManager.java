package org.example.demo.image.service.port;

import org.example.demo.image.domain.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageManager {

    String upload(MultipartFile file, Image image) throws IOException;

    void delete(String filename);
}
