package sharebuy.domain.menu.provider;

import org.springframework.stereotype.Component;
import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.user.domain.Address;
import sharebuy.domain.user.entity.User;

import static sharebuy.domain.menu.domain.TopNavComponent.LOCATION_INFO;
@Component
public final class LocationInfoProvider implements TopNavProvider {


    @Override
    public TopNavComponent getType() {
        return LOCATION_INFO;
    }

    @Override
    public Object getValue(User user) {
        Address address = user.getAddress();
        return address != null ? address.getPrimaryAddress() : null;
    }


}
