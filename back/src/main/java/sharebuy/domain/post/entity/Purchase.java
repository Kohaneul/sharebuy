package sharebuy.domain.post.entity;

import jakarta.persistence.*;
import sharebuy.domain.post.domain.PurchaseStatus;
import sharebuy.domain.user.entity.User;

import java.util.UUID;

@Entity
public class Purchase {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;       // 참여한 사용자

    private Integer quantity;// 참여 수량

    @Enumerated(value = EnumType.STRING)
    private PurchaseStatus status;     // 예: 참여중, 완료 등

    @ManyToOne
    @JoinColumn(name = "post_id",nullable = false)
    private Post post;         // 어떤 게시글에 참여했는지 연결
}
