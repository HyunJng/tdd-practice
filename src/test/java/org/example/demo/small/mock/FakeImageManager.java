package org.example.demo.small.mock;

import org.example.demo.image.domain.Image;
import org.example.demo.image.service.port.ImageManager;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FakeImageManager implements ImageManager {

    private final String urlPrefix;
    private List<String> images = new ArrayList<>();
    private boolean ioExceptionFlg = false;

    public FakeImageManager(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    public void startUploadExceptionTest() {
        ioExceptionFlg = true;
    }

    @Override
    public String upload(MultipartFile file, Image image) throws IOException {
        if (ioExceptionFlg) {
            ioExceptionFlg = false;
            throw new IOException();
        }
        images.add(image.getFilename());
        return urlPrefix + "/" + image.getFilename();
    }

    @Override
    public void delete(String filename) {
        images.remove(filename);
    }
}
