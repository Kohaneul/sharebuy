package sharebuy.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_activity_stats")
public class UserActivityStats extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 평판
    @NotNull
    @Column(nullable = false)
    private int reputationScore;

    // 거래 성공 횟수
    @NotNull
    @Column(nullable = false)
    private int successDeals;

    // 거래 실패 횟수
    @NotNull
    @Column(nullable = false)
    private int failDeals;

    //거래 주최 횟수, INT, NOT NULL
    @NotNull
    @Column(nullable = false)
    private int hostCount;

    // 7. participant_count (거래 참여, INT, NOT NULL)
    @NotNull
    @Column(nullable = false)
    private int participantCount;



}