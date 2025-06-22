package org.example.demo.post.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.example.demo.post.domain.Post;
import org.example.demo.user.infrastructure.entity.UserEntity;

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
