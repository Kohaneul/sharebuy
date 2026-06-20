package sharebuy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sharebuy.domain.post.domain.ParticipationStatus;
import sharebuy.domain.post.domain.PostStatus;
import sharebuy.domain.post.entity.Participation;
import sharebuy.domain.post.entity.Post;
import sharebuy.domain.post.repository.ParticipationRepository;
import sharebuy.domain.post.repository.PostRepository;
import sharebuy.domain.user.domain.UserStatus;
import sharebuy.domain.user.entity.User;
import sharebuy.domain.user.repository.UserRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = SharebuyApplication.class,
        properties = {
                "kakao.map.key=test-key"
        }
)
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ParticipationRepository participationRepository;


    /**
     * 참여인원 테스트
     */
    @Test
    void participationTest(){
        User user = TestFixture.user(UUID.randomUUID(), UserStatus.ACTIVE);
        userRepository.save(user);

        User owner = TestFixture.user(UUID.randomUUID(), UserStatus.ACTIVE);
        userRepository.save(owner);

        Post post = TestFixture.post(UUID.randomUUID(),owner, PostStatus.RECRUITING);
        postRepository.save(post);

        Participation participation = TestFixture.participation(user,post,10000, ParticipationStatus.JOINED);
        Participation saved = participationRepository.save(participation);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getUser().getId())
                .isEqualTo(user.getId());
        assertThat(saved.getPost().getId())
                .isEqualTo(post.getId());
    }
}
