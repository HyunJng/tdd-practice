package org.example.demo.post.controller.dto;

import lombok.Builder;
import lombok.Data;
import org.example.demo.post.domain.Post;

public class PostSave {

    @Data
    public static class Request {
        private String title;
        private String content;
        private long writerId;
    }

    @Data
    public static class Response {
        private String title;
        private long writerId;
        private String writer;
        private String content;
        private String createAt;
        private String modifiedAt;

        @Builder
        public Response(long id, String title, long writerId, String writer, String content, String createAt, String modifiedAt) {
            this.title = title;
            this.writerId = writerId;
            this.writer = writer;
            this.content = content;
            this.createAt = createAt;
            this.modifiedAt = modifiedAt;
        }

        public static Response from(Post post) {
            return Response.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .writerId(post.getWriter().getId())
                    .writer(post.getWriter().getUsername())
                    .content(post.getContent())
                    .createAt(post.getCreateAt())
                    .modifiedAt(post.getModifiedAt())
                    .build();
        }
    }
}
