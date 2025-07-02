package org.example.demo.image.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageMetaJpaRepository extends JpaRepository<ImageMetaEntity, Long> {

    Optional<ImageMetaEntity> findByPost_Id(Long id);
}
