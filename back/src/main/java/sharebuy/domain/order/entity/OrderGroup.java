package sharebuy.domain.order.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.domain.post.domain.Place;
import sharebuy.common.domain.BaseTimeEntity;

import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_group")
public class OrderGroup extends BaseTimeEntity {


    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private UUID postId;

    @NotNull
    @Column(nullable = false)
    private UUID userId;

    @NotBlank
    @Column(nullable = false)
    private int amount;

    @Embedded
    @NotNull
    private Place place;


}
