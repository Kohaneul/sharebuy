package sharebuy.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.Location;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_profile")
public class UserProfile {

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

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
