package org.example.demo.post.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.demo.common.time.port.DateHolder;
import org.example.demo.user.domain.User;

@Getter
public class Post {
    private final Long id;
    private final String title;
    private final String content;
    private final User writer;
    private final String createAt;
    private final String modifiedAt;

    @Builder
    public Post(Long id, String title, String content, User writer, String createAt, String modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.createAt = createAt;
        this.modifiedAt = modifiedAt;
    }

    public static Post from(PostCreate postCreate, User user, DateHolder dateHolder) {
        return Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .writer(user)
                .createAt(dateHolder.now())
                .build();
    }

    public Post update(PostUpdate postUpdate, DateHolder dateHolder) {
        return Post.builder()
                .id(id)
                .title(postUpdate.getTitle())
                .content(postUpdate.getContent())
                .writer(writer)
                .createAt(createAt)
                .modifiedAt(dateHolder.now())
                .build();
    }
}
