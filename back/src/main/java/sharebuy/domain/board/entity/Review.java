package sharebuy.domain.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import sharebuy.domain.user.entity.Users;

import java.util.UUID;

@Entity
public class Review {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;


    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users user;

    private Integer rating;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "purchase_id",nullable = false)
    private Purchase purchase; // 어떤 참여 건에 대한 후기인지


}
