package sharebuy.domain.menu.provider;

import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.menu.entity.Menus;
import sharebuy.domain.user.entity.Users;

public sealed interface TopNavProvider<T> permits LocationInfoProvider,AlarmProvider,MenuProvider{
    TopNavComponent getType();

    T getValue(Users user, Menus menu);
}
