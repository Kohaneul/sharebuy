package sharebuy.domain.post.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.domain.order.domain.PayStatus;
import sharebuy.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "participation")
public class Participation extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank(message = "금액 정보는 필수입니다.")
    @Column(nullable = false,unique = true)
    private int amount;

    @NotNull
    @Column(name = "pay_at", nullable = false, updatable = false)
    private LocalDateTime payAt;

    @NotNull
    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private PayStatus status;

}
