package sharebuy.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.common.domain.ReportType;
import sharebuy.domain.board.entity.Post;
import sharebuy.domain.user.entity.Users;

import java.util.UUID;
import static jakarta.persistence.GenerationType.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "report")
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "reporter_user_id", nullable = false)
    private Users reporterUser;   //신고자

    @NotNull
    @ManyToOne
    @JoinColumn(name = "reported_user_id", nullable = false)
    private Users reportedUser; //신고 대상

    @NotNull
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @NotNull
    @Column(nullable = false)
    private int isProcessed;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType reportType;

}