package sharebuy.domain.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.domain.order.domain.PayStatus;
import sharebuy.domain.user.entity.Users;

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

    @NotNull(message = "아이디는 필수입니다.")
    @Column(nullable = false,unique = true)
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "id")
    private Users user;

    @NotBlank(message = "금액 정보는 필수입니다.")
    @Column(nullable = false,unique = true)
    private int amount;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime payAt;

    @NotNull
    @Column(nullable = false, updatable = false)
    private PayStatus status;

}
