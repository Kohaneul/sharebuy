package sharebuy.domain.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import sharebuy.common.domain.BaseTimeEntity;

import java.util.UUID;
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment extends BaseTimeEntity {
    @NotNull
    @Column(nullable = false)
    private UUID userId;

    @NotNull
    @Column(nullable = false)
    private UUID postId;

    @NotNull
    @Column(nullable = false)
    @Length(min = 2, max = 500, message = "제목은 2자 이상 500자 이하로 입력해야 합니다.")
    private String content;

}
