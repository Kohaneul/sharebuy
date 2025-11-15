package sharebuy.order.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@Getter
@AllArgsConstructor
public class Address {
    private String primaryAddress;
    private String detailAddress;
    private String zipCode;

}
