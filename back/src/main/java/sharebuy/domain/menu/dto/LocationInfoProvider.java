package sharebuy.domain.menu.dto;

import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.menu.entity.Menus;
import sharebuy.domain.user.entity.Users;

public final class LocationInfoProvider implements TopNavProvider<String> {
    @Override
    public TopNavComponent getType() {
        return TopNavComponent.LOCATION_INFO;
    }

    @Override
    public String getValue(Users user, Menus menu) {
        return "";
    }


}
