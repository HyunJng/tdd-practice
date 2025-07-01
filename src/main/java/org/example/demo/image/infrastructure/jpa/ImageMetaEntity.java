package org.example.demo.image.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.example.demo.image.domain.Image;

@NoArgsConstructor
@Entity
@Table(name = "images_meta")
public class ImageMetaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "file_name")
    private String filename;

    @Column(name = "uploader")
    private Long uploader;

    @Column(name = "used")
    private boolean used;

    @Column(name = "create_at")
    private String createAt;

    @Builder
    public ImageMetaEntity(Long id, String filename, Long uploader, boolean used, String createAt) {
        this.id = id;
        this.filename = filename;
        this.uploader = uploader;
        this.used = used;
        this.createAt = createAt;
    }

    public static ImageMetaEntity from(Image image) {
        return ImageMetaEntity.builder()
                .filename(image.getFilename())
                .uploader(image.getUploader())
                .used(false)
                .createAt(image.getCreateAt())
                .build();
    }

    public Image to() {
        return Image.builder()
                .id(id)
                .filename(filename)
                .uploader(uploader)
                .createAt(createAt)
                .build();
    }
}
