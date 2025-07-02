package org.example.demo.image.domain;

import lombok.Builder;
import lombok.Getter;
import org.example.demo.post.domain.Post;

@Getter
public class PostImageUpdate {
    private Long imageId;
    private Post post;

    @Builder
    public PostImageUpdate(Long imageId, Post post) {
        this.imageId = imageId;
        this.post = post;
    }

    public static PostImageUpdate mapToPost(Long id, Post post) {
        return PostImageUpdate.builder()
                .imageId(id)
                .post(post)
                .build();
    }

}
