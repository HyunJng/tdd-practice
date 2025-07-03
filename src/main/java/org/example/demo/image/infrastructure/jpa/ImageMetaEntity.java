package org.example.demo.image.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.demo.image.domain.Image;
import org.example.demo.image.domain.PostImageUpdate;
import org.example.demo.post.infrastructure.jpa.PostEntity;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@NoArgsConstructor
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PostEntity post;

    @Column(name = "create_at")
    private String createAt;

    @LastModifiedDate
    @Column(name = "modify_at")
    private String modifyAt;

    @Builder
    public ImageMetaEntity(Long id, String filename, Long uploader, boolean used, PostEntity post, String createAt) {
        this.id = id;
        this.filename = filename;
        this.uploader = uploader;
        this.used = used;
        this.post = post;
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

    public void usedImage(PostImageUpdate image) {
        this.post = PostEntity.from(image.getPost());
        this.used = true;
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
