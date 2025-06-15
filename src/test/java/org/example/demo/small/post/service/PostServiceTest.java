package org.example.demo.small.post.service;

import org.example.demo.post.domain.PostCreate;
import org.example.demo.small.mock.FakePostRepository;
import org.example.demo.post.domain.Post;
import org.example.demo.post.service.PostService;
import org.example.demo.small.mock.FakeUserRepository;
import org.example.demo.small.mock.TestDateHolder;
import org.example.demo.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

public class PostServiceTest {

    private PostService postService;

    @BeforeEach
    void init() {
        FakePostRepository fakePostRepository = new FakePostRepository();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        postService = PostService.builder()
                .postRepository(fakePostRepository)
                .userRepository(fakeUserRepository)
                .dateHolder(new TestDateHolder("20250615123010"))
                .build();
        User user1 = User.builder()
                .id(1L)
                .username("tester01")
                .password("testerpw1")
                .createAt("20250604000001")
                .build();
        fakePostRepository.save(Post.builder()
                .title("테스트1")
                .content("테스트1 입니다")
                .writer(user1)
                .createAt("20250604000001")
                .build());
        User user2 = User.builder()
                .id(2L)
                .username("tester02")
                .password("testerpw2")
                .createAt("20250604000002")
                .build();
        fakePostRepository.save(Post.builder()
                .title("테스트2")
                .content("테스트2 입니다")
                .writer(user2)
                .createAt("20250604000002")
                .build());

        fakeUserRepository.save(user1);
        fakeUserRepository.save(user2);
    }

    @Test
    void 게시글_목록을_조회할_수_있고_작성날짜_기준_내림차순으로_정렬된다() throws Exception {
        //given
        //when
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> result = postService.findPageOrderByCreateAtDesc(pageable);

        //then
        assertThat(result.getContent().get(0).getCreateAt()).isGreaterThan(result.getContent().get(1).getCreateAt());
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("테스트2");
        assertThat(result.getContent().get(0).getWriter().getUsername()).isEqualTo("tester02");
        assertThat(result.getContent().get(0).getContent()).isEqualTo("테스트2 입니다");
        assertThat(result.getContent().get(0).getCreateAt()).isEqualTo("20250604000002");
        assertThat(result.getContent().get(1).getTitle()).isEqualTo("테스트1");
        assertThat(result.getContent().get(1).getWriter().getUsername()).isEqualTo("tester01");
        assertThat(result.getContent().get(1).getContent()).isEqualTo("테스트1 입니다");
        assertThat(result.getContent().get(1).getCreateAt()).isEqualTo("20250604000001");
    }

    @Test
    void 게시글_작성_시_클라이언트에게_게시글을_반환한다() throws Exception {
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("타이틀")
                .content("내용")
                .writerId(1L)
                .build();

        //when
        Post result = postService.save(postCreate);

        //then
        assertThat(result.getId()).isEqualTo(3L);
        assertThat(result.getTitle()).isEqualTo("타이틀");
        assertThat(result.getContent()).isEqualTo("내용");
        assertThat(result.getCreateAt()).isEqualTo("20250615123010");
        assertThat(result.getWriter().getId()).isEqualTo(1L);
        assertThat(result.getModifyAt()).isNull();
    }

    @Test
    void 선택한_게시글을_조회할_수_있다() throws Exception {
        //given
        //when
        Post post = postService.getById(1L);

        //then
        assertThat(post.getId()).isEqualTo(1L);
        assertThat(post.getTitle()).isEqualTo("테스트1");
        assertThat(post.getContent()).isEqualTo("테스트1 입니다");
        assertThat(post.getWriter().getId()).isEqualTo(1L);
        assertThat(post.getCreateAt()).isEqualTo("20250604000001");
        assertThat(post.getModifyAt()).isNull();
    }

    @Test
    void 게시글_작성자는_게시글을_수정할_수_있다() throws Exception {
        //given
        
        //when

        //then
    }

    @Test
    void 게시글_작성자가_아니면_게시글_수정할_수_없다() throws Exception {
        //given
        
        //when

        //then
    }

    @Test
    void 게시글_작성자는_게시글을_삭제할_수_있다() throws Exception {
        //given

        //when

        //then
    }

    @Test
    void 게시글_작성자가_아니면_게시글을_삭제할_수_없다() throws Exception {
        //given

        //when

        //then
    }

}
