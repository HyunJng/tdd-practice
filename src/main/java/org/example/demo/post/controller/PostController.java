package org.example.demo.post.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.post.controller.dto.PostDetail;
import org.example.demo.post.controller.dto.PostList;
import org.example.demo.post.controller.dto.PostSave;
import org.example.demo.post.domain.PostCreate;
import org.example.demo.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시글 CRUD")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public Page<PostList.Response> findPosts(@Valid PostList.Request request) {
        return postService.findPageOrderByCreateAtDesc(request.to())
                .map(PostList.Response::from);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDetail.Response> getDetail(@PathVariable("id") long id) {
        PostDetail.Response response = PostDetail.Response.from(postService.getById(id));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PostSave.Response> save(@RequestBody PostSave.Request request) { // TODO: JWT 적용 후 수정
        PostSave.Response response = PostSave.Response.from(postService.save(PostCreate.from(request)));
        return ResponseEntity.ok(response);
    }
}
