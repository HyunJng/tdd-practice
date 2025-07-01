package org.example.demo.image.service.port;

import org.example.demo.image.domain.Image;

import java.util.Optional;

public interface ImageMetaRepository {

    Image save(Image image);

    Optional<Image> findById(long id);
}
