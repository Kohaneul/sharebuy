import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sharebuy.common.exception.ShareBuyException;
import sharebuy.domain.post.entity.Post;
import sharebuy.domain.post.repository.ParticipationRepository;
import sharebuy.domain.post.repository.PostRepository;
import sharebuy.domain.post.service.PostService;
import sharebuy.domain.user.entity.User;
import sharebuy.domain.user.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static sharebuy.common.exception.ErrorCode.*;
import static sharebuy.domain.post.domain.PostStatus.CLOSED;
import static sharebuy.domain.post.domain.PostStatus.RECRUITING;
import static sharebuy.domain.user.domain.UserStatus.ACTIVE;
import static sharebuy.domain.user.domain.UserStatus.SUSPENDED;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private ParticipationRepository participationRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private PostService postService;

    /**
     * ex1
     * 본인의 게시글을 참여할 경우
     */
    @Test
    void participate_ex1(){
        User owner = TestFixture.user(UUID.randomUUID(), ACTIVE);
        Post post = TestFixture.post(UUID.randomUUID(),owner, RECRUITING);

        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        ShareBuyException ex = Assertions.catchThrowableOfType(
                () -> postService.participate(post.getId(), owner.getId()),
                ShareBuyException.class);
        Assertions.assertThat(ex.getErrorCode()).isEqualTo(SELF_PARTICIPATION_NOT_ALLOWED);
    }

    /**
     * ex2
     * 이미 완료된 게시글을 참여할 경우
     */
    @Test
    void participation_ex2(){
        User owner = TestFixture.user(UUID.randomUUID(),ACTIVE);
        Post post = TestFixture.post(UUID.randomUUID(),owner, CLOSED);

        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));
        when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        ShareBuyException ex
                = Assertions.catchThrowableOfType(() -> postService.participate(post.getId(), owner.getId()), ShareBuyException.class);

        Assertions.assertThat(ex.getErrorCode()).isEqualTo(SOLD_OUT);
    }


    /**
     * ex3
     * 게시글 참여자가 참여 정지된 참여자라면
     */
    @Test
    void participation_ex3(){
        User owner = TestFixture.user(UUID.randomUUID(), SUSPENDED);
        Post post = TestFixture.post(UUID.randomUUID(),owner, RECRUITING);

        when(userRepository.findById(owner.getId())).thenReturn(Optional.of(owner));

        ShareBuyException ex
                = Assertions.catchThrowableOfType(() -> postService.participate(post.getId(), owner.getId()), ShareBuyException.class);

        Assertions.assertThat(ex.getErrorCode()).isEqualTo(USER_NOT_ACTIVE);
    }




}
