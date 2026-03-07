package sharebuy.domain.post.domain;

import jakarta.persistence.Column;
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
    @Column(name = "place_name")
    private String placeName;

}
