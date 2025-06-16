package org.example.demo.post.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.post.controller.dto.PostDetail;
import org.example.demo.post.controller.dto.PostList;
import org.example.demo.post.controller.dto.PostSave;
import org.example.demo.post.controller.dto.PostChange;
import org.example.demo.post.domain.PostCreate;
import org.example.demo.post.domain.PostUpdate;
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

    @PutMapping("/{id}")
    public ResponseEntity<PostChange.Response> update(@PathVariable("id") long id,
                       @RequestBody PostChange.Request postChange) { // TODO: JWT 적용 후 수정
        PostChange.Response response = PostChange.Response.from(postService.update(PostUpdate.from(id, postChange), postChange.getWriterId()));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long postId,
                                       @RequestParam long userId) { //TODO: JWT 적용 후 수정
        postService.delete(postId, userId);
        return ResponseEntity.ok().build();
    }
}
