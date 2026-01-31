package sharebuy.domain.post.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import sharebuy.domain.menu.entity.Menus;
import sharebuy.domain.post.domain.Appointment;
import sharebuy.domain.post.domain.PostStatus;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.domain.user.entity.User;

import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;


@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @NotBlank(message = "게시글 아이디는 필수입니다.")
    @Length(min = 2, max = 100, message = "제목은 2자 이상 100자 이하로 입력해야 합니다.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "게시글 아이디는 필수입니다.")
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
    private Menus menu;

    @OneToMany(mappedBy = "post", cascade = ALL)
    private List<Purchase> purchases; // 해당 게시글의 공동구매 참여 목록

}
