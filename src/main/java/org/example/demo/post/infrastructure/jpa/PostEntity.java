package org.example.demo.post.infrastructure.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.example.demo.image.infrastructure.jpa.ImageMetaEntity;
import org.example.demo.post.domain.Post;
import org.example.demo.user.infrastructure.jpa.UserEntity;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "modify_at")
    private String modifyAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity writer;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<ImageMetaEntity> images;

    public static PostEntity from(Post post) {
        return PostEntity.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createAt(post.getCreateAt())
                .modifyAt(post.getModifiedAt())
                .writer(UserEntity.from(post.getWriter()))
                .build();
    }

    public Post to() {
        return Post.builder()
                .id(id)
                .title(title)
                .content(content)
                .createAt(createAt)
                .modifiedAt(modifyAt)
                .writer(writer.to())
                .build();
    }
}
