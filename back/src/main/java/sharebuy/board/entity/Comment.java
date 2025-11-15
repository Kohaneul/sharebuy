package sharebuy.board.entity;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class Comment {
    @NotNull
    @Column(nullable = false)
    private UUID userId;

}
