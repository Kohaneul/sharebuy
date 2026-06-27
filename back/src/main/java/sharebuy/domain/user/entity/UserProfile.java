package sharebuy.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.common.domain.Location;
import sharebuy.domain.post.entity.Post;

import java.util.UUID;

@Builder
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_profile")
public class UserProfile extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @Embedded
    private Location location;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Column(name = "nick_name", nullable = false, unique = true)
    private String nickName;


}
