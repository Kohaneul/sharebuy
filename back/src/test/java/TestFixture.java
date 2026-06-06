import sharebuy.common.domain.RoleType;
import sharebuy.domain.post.domain.PostStatus;
import sharebuy.domain.post.entity.Post;
import sharebuy.domain.user.domain.UserStatus;
import sharebuy.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import static sharebuy.domain.order.domain.Category.ETC;
import static sharebuy.domain.post.domain.PurchaseType.ONLINE;

public class TestFixture {

    public static User user(UUID id,UserStatus userStatus) {
        return User.builder()
                .id(id)
                .userStatus(userStatus)
                .roleType(RoleType.USER)
                .loginId("test")
                .build();
    }

    public static Post post(UUID postId, User owner, PostStatus status) {
        return new Post(
                postId,
                owner,
                "제목",
                "내용",
                ONLINE,
                "쿠팡",
                "1234",
                "https://test.com",
                10000,
                LocalDateTime.now(),
                status,
                null,
                List.of(),
                List.of(),
                0,
                5,
                ETC
        );
    }
}
