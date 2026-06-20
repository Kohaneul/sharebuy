package sharebuy.domain.post.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import sharebuy.common.exception.ErrorCode;
import sharebuy.common.exception.ShareBuyException;
import sharebuy.domain.order.domain.Category;
import sharebuy.domain.post.domain.Appointment;
import sharebuy.domain.post.domain.PostStatus;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.domain.post.domain.PurchaseType;
import sharebuy.domain.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static sharebuy.common.exception.ErrorCode.*;


@Entity
@Getter
@Builder
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

    @ManyToOne(fetch=LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @NotBlank(message = "게시글 제목은 필수입니다.")
    @Length(min = 2, max = 100, message = "제목은 2자 이상 100자 이하로 입력해야 합니다.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "게시글 내용은 필수입니다.")
    @Column(nullable = false)
    private String content;

    @NotNull
    @Column(name = "purchase_type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private PurchaseType purchaseType;

    @NotBlank(message = "구입처는 필수입니다.")
    @Column(name = "purchase_place",nullable = false)
    private String purchasePlace;

    @NotBlank(message = "상품 코드는 필수입니다.")
    @Column(name = "product_code",nullable = false)
    private String productCode;

    @NotBlank(message = "상품 구매 url은 필수입니다.")
    @Column(name = "purchase_url")
    private String purchaseUrl;

    @NotNull(message = "총 구매 금액은 필수입니다.")
    @Column(name = "total_price",nullable = false)
    private Integer totalPrice;

    @NotNull(message = "인당 참여 금액은 필수입니다.")
    @Column(name = "per_price", nullable = false)
    private Integer perPrice;

    @NotNull(message = "구매 시점은 필수입니다.")
    @Column(name = "purchase_at",nullable = false)
    private LocalDateTime purchaseAt;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @NotNull
    @Column(nullable = false)
    @Embedded
    private Appointment appointment;

    @OneToMany(mappedBy = "post", cascade = ALL)
    private List<Purchase> purchases;

    @ElementCollection
    @Column(name = "img_url")
    private List<String> imgUrl;

    @Column(name = "current_participants",nullable = false)
    private Integer currentParticipants = 0;

    @Column(nullable = false,name = "max_participants")
    private Integer maxParticipants;@NotNull(message = "인당 참여 금액은 필수입니다.")

    @NotNull
    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private Category category;


    private boolean isOwner(User user){
        return this.getUser().getId().equals(user.getId());
    }

    public void validateOwnerUser(User user){
        if(!isOwner(user)){
            throw new ShareBuyException(NOT_POST_OWNER);
        }

    }
    public void validateCanParticipate(User user) {
        //1. 이미 마감된 건일때
        if (this.status == PostStatus.CLOSED) {
            throw new ShareBuyException(SOLD_OUT);
        }

        //2. 모집중인 게시글이 아닐때
        if(this.status != PostStatus.RECRUITING){
            throw new ShareBuyException(ErrorCode.NOT_RECRUITABLE);
        }

        //3. 본인 글에 참여할때
        if(isOwner(user)){
            throw new ShareBuyException(SELF_PARTICIPATION_NOT_ALLOWED);
        }


        //4. 게시글 작성자 상태 체크
        this.getUser().validateUserActive();
    }


}


