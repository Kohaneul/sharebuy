package sharebuy.domain.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.domain.user.entity.Users;

import java.util.UUID;
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "comment")
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "id")
    private Post post;

    @NotNull
    @Column(nullable = false)
    @Length(min = 2, max = 500, message = "제목은 2자 이상 500자 이하로 입력해야 합니다.")
    private String content;

}
