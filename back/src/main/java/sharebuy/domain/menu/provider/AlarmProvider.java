package sharebuy.domain.menu.provider;

import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.menu.entity.Menus;
import sharebuy.domain.user.entity.Users;

public final class AlarmProvider implements TopNavProvider<Integer> {
    @Override
    public TopNavComponent getType() {
        return null;
    }

    @Override
    public Integer getValue(Users user, Menus menu) {
        return 5;
    }

}
