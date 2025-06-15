package org.example.demo.small.mock;

import org.example.demo.post.domain.Post;
import org.example.demo.post.service.port.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class FakePostRepository implements PostRepository {

    private AtomicLong generatedId = new AtomicLong();
    private List<Post> data = new ArrayList<>();

    @Override
    public Page<Post> findAllOrderByIdDesc(Pageable pageable) {
        List<Post> sorted = data.stream().sorted(Comparator.comparingLong(Post::getId).reversed()).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), sorted.size());
        List<Post> pageContent = sorted.subList(start, end);

        return new PageImpl<>(pageContent, pageable, sorted.size());
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == null || post.getId() == 0) {
            Post newPost = Post.builder()
                    .id(generatedId.incrementAndGet())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .writer(post.getWriter())
                    .createAt(post.getCreateAt())
                    .modifyAt(post.getModifyAt())
                    .build();
            data.add(newPost);
            return newPost;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), post.getId()));
            data.add(post);
            return post;
        }
    }

    @Override
    public Optional<Post> findById(Long id) {
        return data.stream().filter(item -> Objects.equals(item.getId(), id)).findFirst();
    }
}
