package org.example.demo.post.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.demo.post.controller.dto.PostSave;

import java.util.List;

@Getter
public class PostCreate {

    private final String title;
    private final String content;
    private final Long writerId;
    private final List<Long> images;

    @Builder
    public PostCreate(String title, String content, long writerId, List<Long> images) {
        this.title = title;
        this.content = content;
        this.writerId = writerId;
        this.images = images;
    }

    public static PostCreate from(PostSave.Request request, long userId) {
        return PostCreate.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writerId(userId)
                .images(request.getImages())
                .build();
    }
}
