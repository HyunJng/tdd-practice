package org.example.demo.post.controller.docs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.demo.auth.domain.LoginUser;
import org.example.demo.post.controller.dto.PostChange;
import org.example.demo.post.controller.dto.PostDetail;
import org.example.demo.post.controller.dto.PostList;
import org.example.demo.post.controller.dto.PostSave;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

@Tag(name = "게시글 CRUD")
public interface PostControllerSwagger {

    @Operation(
            summary = "게시글 목록 조회",
            description = "게시글 목록과 page 정보를 반환받습니다"
    )
    Page<PostList.Response> findPosts(PostList.Request request);

    @Operation(
            summary = "게시글 상세 조회",
            description = "특정 게시글에 대한 정보를 반환받습니다"
    )
    ResponseEntity<PostDetail.Response> getDetail(long id);

    @Operation(
            summary = "게시글 저장",
            security = @SecurityRequirement(name = "JWT"),
            description = "회원은 게시글을 저장할 수 있습니다."
    )
    ResponseEntity<PostSave.Response> save(@Parameter(hidden = true) LoginUser loginUser, PostSave.Request request);

    @Operation(
            summary = "게시글 수정",
            security = @SecurityRequirement(name = "JWT"),
            description = "회원은 게시글을 수정할 수 있습니다."
    )
    ResponseEntity<PostChange.Response> update(@Parameter(hidden = true) LoginUser loginUser, long id, PostChange.Request postChange);

    @Operation(
            summary = "게시글 삭제",
            security = @SecurityRequirement(name = "JWT"),
            description = "회원은 게시글을 삭제할 수 있습니다."
    )
    ResponseEntity<Void> delete(@Parameter(hidden = true) LoginUser loginUser, long postId);
 }
