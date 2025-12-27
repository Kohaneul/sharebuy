package sharebuy.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.domain.board.entity.Participation;
import sharebuy.domain.board.entity.Post;
import sharebuy.domain.user.entity.Users;

import java.util.UUID;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payment")
public class Payment extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users user;    //참여자

    @NotNull
    @ManyToOne
    @JoinColumn(name = "participant_id",nullable = false)
    private Participation participant;

    @NotBlank
    @Column(nullable = false)
    private int amount;

    @NotBlank
    @Column(nullable = false)
    private String pg_tid;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "group_id",nullable = false)
    private OrderGroup orderGroup;


    //참여자
    @NotBlank
    @Column(nullable = false)
    private int quantity;

}
