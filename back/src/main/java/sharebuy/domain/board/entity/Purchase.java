package sharebuy.domain.board.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Purchase {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    private Long userId;       // 참여한 사용자
    private Integer quantity;  // 참여 수량
    private String status;     // 예: 참여중, 완료 등

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;         // 어떤 게시글에 참여했는지 연결
}
