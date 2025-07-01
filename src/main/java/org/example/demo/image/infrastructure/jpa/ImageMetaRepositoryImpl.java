package org.example.demo.image.infrastructure.jpa;

import lombok.RequiredArgsConstructor;
import org.example.demo.image.domain.Image;
import org.example.demo.image.service.port.ImageMetaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
}
