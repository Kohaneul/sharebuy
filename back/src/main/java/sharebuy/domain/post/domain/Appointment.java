package sharebuy.domain.post.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
public class Appointment{

    @Embedded
    @NotNull(message = "장소는 필수입니다.")
    private Place place;

    @NotNull(message = "약속 일시는 필수입니다.")
    private LocalDateTime appointmentTime;
}
