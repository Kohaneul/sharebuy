package sharebuy.domain.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
public class Review {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private UUID boardId;

    @NotNull
    @Column(nullable = false)
    private UUID userId;

    private Integer rating;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "purchase_id")
    private Purchase purchase; // 어떤 참여 건에 대한 후기인지


}
