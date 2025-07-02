package org.example.demo.image.service.port;

import org.example.demo.image.domain.Image;
import org.example.demo.image.domain.PostImageUpdate;

import java.util.List;
import java.util.Optional;

public interface ImageMetaRepository {

    Image save(Image image);

    Optional<Image> findById(long id);

    void updateToUsedImage(List<PostImageUpdate> images);

    Optional<Image> findByPostId(long postId);
}
