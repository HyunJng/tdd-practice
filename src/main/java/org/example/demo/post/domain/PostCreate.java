package org.example.demo.post.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreate {

    private final String title;
    private final String content;
    private final long writerId;

    @Builder
    public PostCreate(String title, String content, long writerId) {
        this.title = title;
        this.content = content;
        this.writerId = writerId;
    }
}
