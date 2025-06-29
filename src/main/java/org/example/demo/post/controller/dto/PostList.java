package org.example.demo.post.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.example.demo.post.domain.Post;
import org.springframework.data.domain.PageRequest;

public class PostList {

    @Data
    @Schema(name = "PostListRequest", description = "게시글 목록 조회 요청")
    public static class Request {
        @Pattern(regexp = "\\d+")
        private String page;
        @Pattern(regexp = "\\d+")
        private String size;

        public PageRequest to() {
            return PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
        }
    }

    @Data
    @Builder
    @Schema(name = "PostListResponse", description = "게시글 목록 조회 응답")
    public static class Response {
        private long id;
        private String title;
        private String content;
        private long writerId;
        private String writer;
        private String createAt;

        public static PostList.Response from(Post post) {
            return PostList.Response.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .writerId(post.getWriter().getId())
                    .writer(post.getWriter().getUsername())
                    .createAt(post.getCreateAt())
                    .build();
        }
    }
}
