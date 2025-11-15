package sharebuy.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import sharebuy.common.domain.BaseTimeEntity;

import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.persistence.GenerationType.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user_activity_stats")
public class UserActivityStats extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = UUID)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private UUID userId;

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