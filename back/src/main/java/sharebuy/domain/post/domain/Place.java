package sharebuy.domain.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;
import sharebuy.common.domain.Location;
import sharebuy.domain.user.domain.Address;


@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Place {
    @Column(name = "place_name")
    private String placeName;

    @Embedded
    private Address address;

}
