package org.example.demo.post.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo.auth.service.port.JwtManager;
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
    private final JwtExtractor jwtExtractor;
    private final JwtManager jwtManager;

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
    public ResponseEntity<PostSave.Response> save(HttpServletRequest httpServletRequest,
                                                  @RequestBody PostSave.Request request) {
        long userId = getUserId(httpServletRequest);
        PostSave.Response response = PostSave.Response.from(postService.save(PostCreate.from(request, userId)));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostChange.Response> update(HttpServletRequest httpServletRequest,
                                                      @PathVariable("id") long id,
                                                      @RequestBody PostChange.Request postChange) {
        long userId = getUserId(httpServletRequest);
        PostChange.Response response = PostChange.Response.from(postService.update(PostUpdate.from(id, postChange), userId));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(HttpServletRequest httpServletRequest,
                                       @PathVariable("id") long postId) {
        long userId = getUserId(httpServletRequest);
        postService.delete(postId, userId);
        return ResponseEntity.ok().build();
    }

    private long getUserId(HttpServletRequest httpServletRequest) {
        String token = jwtExtractor.resolveToken(httpServletRequest);
        jwtManager.validateToken(token);
        return jwtManager.getUserIdFromToken(token);
    }
}
