package sharebuy.domain.alarm.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.domain.alarm.type.AlarmType;
import sharebuy.domain.user.entity.User;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "alarm")
public class Alarm extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private AlarmType type;

    @Column(nullable = false)
    private boolean read; // 읽었는지 여부

    @Column(columnDefinition = "text")
    private String message; // 알람 내용
}
