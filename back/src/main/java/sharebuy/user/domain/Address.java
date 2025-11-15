package sharebuy.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Address {

    @NotBlank(message = "주소1은 필수입니다.")
    @Column(nullable = false)
    private String primaryAddress;

    @NotBlank(message = "상세 주소는 필수입니다.")
    @Column(nullable = false)
    private String detailAddress;

    @NotBlank(message = "우편번호는 필수입니다.")
    @Column(nullable = false)
    private String zipCode;

}
