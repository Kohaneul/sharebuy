package sharebuy.domain.board.domain;

import jakarta.persistence.Embeddable;
import lombok.*;
import sharebuy.common.domain.Location;


@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Place {
    private Location location;
    private String placeName;

}
