package org.example.demo.post.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.example.demo.post.domain.Post;

public class PostChange {

    @Data
    @Schema(name = "PostChangeRequest", description = "게시글 수정 요청")
    public static class Request {
        private String title;
        private String content;
    }

    @Data
    @Schema(name = "PostChangeResponse", description = "게시글 수정 응답")
    public static class Response {
        private long id;
        private String title;
        private long writerId;
        private String writer;
        private String content;
        private String createAt;
        private String modifiedAt;

        @Builder
        public Response(long id, String title, long writerId, String writer, String content, String createAt, String modifiedAt) {
            this.id = id;
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
