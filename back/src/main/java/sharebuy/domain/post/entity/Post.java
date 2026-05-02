package sharebuy.domain.post.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import sharebuy.domain.menu.entity.Menu;
import sharebuy.domain.post.domain.Appointment;
import sharebuy.domain.post.domain.PostStatus;
import sharebuy.common.domain.BaseTimeEntity;

import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "post",
        indexes = {
                @Index(name = "idx_post_status_lat_lon", columnList = "status, latitude, longitude")
        }
)
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = true)
    private String avatar;

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(name = "nick_name")
    private String nickName;

    @NotBlank(message = "게시글 제목은 필수입니다.")
    @Length(min = 2, max = 100, message = "제목은 2자 이상 100자 이하로 입력해야 합니다.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "게시글 내용은 필수입니다.")
    @Column(nullable = false)
    private String content;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @NotNull
    @Column(nullable = false)
    @Embedded
    private Appointment appointment;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "menu_id",nullable = false)
    private Menu menu;

    @OneToMany(mappedBy = "post", cascade = ALL)
    private List<Purchase> purchases; // 해당 게시글의 공동구매 참여 목록

    @ElementCollection
    @Column(name = "img_url")
    private List<String> imgUrl;

    @Column(name = "max_participants")
    private Integer maxParticipants;

}
