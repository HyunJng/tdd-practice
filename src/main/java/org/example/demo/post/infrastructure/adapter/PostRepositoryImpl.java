package org.example.demo.post.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.example.demo.post.domain.Post;
import org.example.demo.post.infrastructure.entity.PostEntity;
import org.example.demo.post.infrastructure.jpa.PostJpaRepository;
import org.example.demo.post.service.port.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    @Override
    public Page<Post> findAllOrderByIdDesc(Pageable pageable) {
        return postJpaRepository.findAllByOrderByCreateAtDesc(pageable).map(PostEntity::to);
    }

    @Override
    public Post save(Post post) {
        return postJpaRepository.save(PostEntity.from(post)).to();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postJpaRepository.findById(id).map(PostEntity::to);
    }

    @Override
    public void delete(long id) {
        postJpaRepository.deleteById(id);
    }
}
