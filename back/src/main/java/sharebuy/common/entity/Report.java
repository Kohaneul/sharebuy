package sharebuy.common.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sharebuy.common.domain.BaseTimeEntity;
import sharebuy.common.domain.ReportType;

import java.util.UUID;
import static jakarta.persistence.GenerationType.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "report")
public class Report extends BaseTimeEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = UUID)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private UUID reporterUserId;

    @NotNull
    @Column(nullable = false)
    private UUID reportedUserId;

    @NotNull
    @Column(nullable = false)
    private UUID postId;

    @NotNull
    @Column(nullable = false)
    private boolean isProcessed;

    @NotNull
    @Column(nullable = false, length = 1000)
    private ReportType reportType;

}