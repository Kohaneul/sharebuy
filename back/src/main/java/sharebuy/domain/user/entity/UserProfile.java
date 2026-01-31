package sharebuy.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.common.domain.Location;
import sharebuy.domain.post.entity.Post;

import java.util.UUID;


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

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @Embedded
    private Location location;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Column(nullable = false, unique = true)
    private String nickName;


}
