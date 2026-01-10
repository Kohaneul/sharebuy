package sharebuy.domain.menu.provider;

import org.springframework.stereotype.Component;
import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.menu.entity.Menus;
import sharebuy.domain.user.entity.Users;

import static sharebuy.domain.menu.domain.TopNavComponent.LOCATION_INFO;
@Component
public final class LocationInfoProvider implements TopNavProvider {


    @Override
    public TopNavComponent getType() {
        return LOCATION_INFO;
    }

    @Override
    public Object getValue(Users user, Menus menu) {
        return value;
    }


}
