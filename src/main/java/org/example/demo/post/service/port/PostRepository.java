package org.example.demo.post.service.port;

import org.example.demo.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostRepository {

    Page<Post> findAllOrderByIdDesc(Pageable pageable);

    Post save(Post post);

    Optional<Post> findById(Long id);
}
