package org.example.demo.image.infrastructure.jpa;

import lombok.RequiredArgsConstructor;
import org.example.demo.image.domain.Image;
import org.example.demo.image.domain.PostImageUpdate;
import org.example.demo.image.service.port.ImageMetaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ImageMetaRepositoryImpl implements ImageMetaRepository {

    private final ImageMetaJpaRepository imageMetaJpaRepository;

    @Override
    public Image save(Image image) {
        return imageMetaJpaRepository.save(ImageMetaEntity.from(image)).to();
    }

    @Override
    public Optional<Image> findById(long id) {
        return imageMetaJpaRepository.findById(id).map(ImageMetaEntity::to);
    }

    @Override
    public void updateToUsedImage(List<PostImageUpdate> images) {
        Map<Long, PostImageUpdate> imagesMap = images.stream()
                .collect(Collectors.toMap(PostImageUpdate::getImageId, Function.identity()));

        List<ImageMetaEntity> findImages = imageMetaJpaRepository.findAllById(imagesMap.keySet());

        findImages.forEach(image -> image.usedImage(imagesMap.get(image.getId())));

        imageMetaJpaRepository.saveAll(findImages);
    }

    @Override
    public Optional<Image> findByPostId(long postId) {
        return imageMetaJpaRepository.findByPost_Id(postId).map(ImageMetaEntity::to);
    }
}
