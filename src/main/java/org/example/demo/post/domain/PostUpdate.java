package org.example.demo.post.domain;

import lombok.Builder;
import lombok.Getter;

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
}
