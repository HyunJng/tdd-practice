package org.example.demo.post.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.demo.post.controller.dto.PostChange;

@Getter
public class PostUpdate {

    private final long id;
    private final String title;
    private final String content;

    @Builder
    public PostUpdate(long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public static PostUpdate from(long postId, PostChange.Request postChange) {
        return PostUpdate.builder()
                .id(postId)
                .title(postChange.getTitle())
                .content(postChange.getContent())
                .build();
    }
}
