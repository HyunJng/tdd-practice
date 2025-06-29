package org.example.demo.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.auth.domain.LoginUser;
import org.example.demo.auth.service.port.CurrentUser;
import org.example.demo.post.controller.dto.PostChange;
import org.example.demo.post.controller.dto.PostDetail;
import org.example.demo.post.controller.dto.PostList;
import org.example.demo.post.controller.dto.PostSave;
import org.example.demo.post.domain.PostCreate;
import org.example.demo.post.domain.PostUpdate;
import org.example.demo.post.service.PostService;
import org.example.demo.post.controller.docs.PostControllerSwagger;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController implements PostControllerSwagger {

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
    public ResponseEntity<PostSave.Response> save(@CurrentUser LoginUser loginUser,
                                                  @RequestBody PostSave.Request request) {
        PostSave.Response response = PostSave.Response.from(postService.save(PostCreate.from(request, loginUser.getId())));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostChange.Response> update(@CurrentUser LoginUser loginUser,
                                                      @PathVariable("id") long id,
                                                      @RequestBody PostChange.Request postChange) {
        PostChange.Response response = PostChange.Response.from(postService.update(PostUpdate.from(id, postChange), loginUser.getId()));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@CurrentUser LoginUser loginUser,
                                       @PathVariable("id") long postId) {
        postService.delete(postId, loginUser.getId());
        return ResponseEntity.ok().build();
    }
}
