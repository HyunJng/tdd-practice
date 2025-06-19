package org.example.demo.post.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.example.demo.exception.domain.CommonException;
import org.example.demo.exception.domain.ErrorCode;
import org.example.demo.common.service.port.DateHolder;
import org.example.demo.post.domain.Post;
import org.example.demo.post.domain.PostCreate;
import org.example.demo.post.domain.PostUpdate;
import org.example.demo.post.service.port.PostRepository;
import org.example.demo.user.domain.User;
import org.example.demo.user.service.port.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final DateHolder dateHolder;

    public Page<Post> findPageOrderByCreateAtDesc(Pageable pageable) {
        return postRepository.findAllOrderByIdDesc(pageable);
    }

    public Post save(PostCreate postCreate) {
        User user = userRepository.findById(postCreate.getWriterId())
                .orElseThrow(() -> new CommonException(ErrorCode.RESOURCE_NOT_FOUND, "USER"));
        return postRepository.save(Post.from(postCreate, user, dateHolder));
    }

    public Post getById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.RESOURCE_NOT_FOUND, "Post"));
    }

    public Post update(PostUpdate postUpdate, long userId) {
        Post post = postRepository.findById(postUpdate.getId())
                .orElseThrow(() -> new CommonException(ErrorCode.RESOURCE_NOT_FOUND, "Post"));
        if (!post.getWriter().getId().equals(userId)) {
            throw new CommonException(ErrorCode.UNAUTHORIZED);
        }

        post = post.update(postUpdate, dateHolder);
        return postRepository.save(post);
    }

    public void delete(long postId, long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new CommonException(ErrorCode.RESOURCE_NOT_FOUND, "Post"));
        if (!post.getWriter().getId().equals(userId)) {
            throw new CommonException(ErrorCode.UNAUTHORIZED);
        }
        postRepository.delete(postId);
    }
}
