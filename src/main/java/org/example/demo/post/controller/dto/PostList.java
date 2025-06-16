package org.example.demo.post.controller.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.example.demo.post.domain.Post;
import org.springframework.data.domain.PageRequest;

public class PostList {

    @Data
    public static class Request {
        @Pattern(regexp = "\\d+", message = "BAD_REQUEST_PARAM_FORMAT")
        private String page;
        @Pattern(regexp = "\\d+", message = "BAD_REQUEST_PARAM_FORMAT")
        private String size;

        public PageRequest to() {
            return PageRequest.of(Integer.parseInt(page), Integer.parseInt(size));
        }
    }

    @Data
    @Builder
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
