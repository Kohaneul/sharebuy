package sharebuy.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.common.domain.Location;

import java.util.UUID;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_profile")
public class UserProfile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "아이디는 필수입니다.")
    @Column(nullable = false)
    private UUID userId;

    @NotBlank(message = "게시글 아이디는 필수입니다.")
    @Column(nullable = false)
    private String boardId;

    @Embedded
    private Location location;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Column(nullable = false, unique = true)
    private String nickName;


}
