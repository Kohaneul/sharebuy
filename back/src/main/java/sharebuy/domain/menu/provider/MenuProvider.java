package sharebuy.domain.menu.provider;

import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.menu.entity.Menus;
import sharebuy.domain.user.entity.Users;

import java.util.List;

public final class MenuProvider implements TopNavProvider<List<String>> {
    @Override
    public TopNavComponent getType() {
        return TopNavComponent.MENU;
    }

    @Override
    public List<String> getValue(Users user, Menus menu) {
        return List.of();
    }

}
