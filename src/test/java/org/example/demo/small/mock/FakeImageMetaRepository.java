package org.example.demo.small.mock;

import org.example.demo.image.domain.Image;
import org.example.demo.image.service.port.ImageMetaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeImageMetaRepository implements ImageMetaRepository {

    private AtomicLong generatedId = new AtomicLong(2);
    private List<Image> data = new ArrayList<>();

    @Override
    public Image save(Image image) {
        if (image.getId() == null || image.getId() == 0L) {
            Image newImage = Image.builder()
                    .id(generatedId.incrementAndGet())
                    .filename(image.getFilename())
                    .uploader(image.getUploader())
                    .fileUrl(image.getFileUrl())
                    .createAt(image.getCreateAt())
                    .build();
            data.add(newImage);
            return newImage;
        }
        data.removeIf(item -> Objects.equals(item.getId(), image.getId()));
        data.add(image);
        return image;
    }

    @Override
    public Optional<Image> findById(long id) {
        return data.stream().filter(item -> item.getId() == id).findAny();
    }
}
