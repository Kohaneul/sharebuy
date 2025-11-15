package sharebuy.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;

import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payment")
public class Payment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private UUID userId;    //참여자

    @NotNull
    @Column(nullable = false)
    private UUID participantId;

    @NotBlank
    @Column(nullable = false)
    private int amount;

    @NotBlank
    @Column(nullable = false)
    private String pg_tid;

    @NotNull
    @Column(nullable = false)
    private UUID postId;

    @NotNull
    @Column(nullable = false)
    private UUID groupId;


    //참여자
    @NotBlank
    @Column(nullable = false)
    private int quantity;

}
