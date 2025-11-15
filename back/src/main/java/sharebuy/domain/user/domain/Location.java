package sharebuy.domain.user.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Location {

    // 위도: -90.0 ~ 90.0 범위
    @NotNull(message = "위도는 필수입니다.")
    @DecimalMin(value = "-90.0", message = "위도 범위(최소 -90.0)를 벗어났습니다.")
    @DecimalMax(value = "90.0", message = "위도 범위(최대 90.0)를 벗어났습니다.")
    private double latitude;

    // 경도: -180.0 ~ 180.0 범위
    @NotNull(message = "경도는 필수입니다.") // 👈 메시지 수정
    @DecimalMin(value = "-180.0", message = "경도 범위(최소 -180.0)를 벗어났습니다.") // 👈 범위 수정
    @DecimalMax(value = "180.0", message = "경도 범위(최대 180.0)를 벗어났습니다.") // 👈 범위 수정
    private double longitude;

}
