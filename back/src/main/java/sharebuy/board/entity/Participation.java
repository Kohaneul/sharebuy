package sharebuy.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.order.domain.PayStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "participation")
public class Participation extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "아이디는 필수입니다.")
    @Column(nullable = false,unique = true)
    private UUID userId;

    @NotBlank(message = "게시글 정보는 필수입니다.")
    @Column(nullable = false,unique = true)
    private UUID postId;


    @NotBlank(message = "상대방 아이디 정보는 필수입니다.")
    @Column(nullable = false,unique = true)
    private UUID buyerId;

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
