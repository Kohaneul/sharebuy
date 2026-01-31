package sharebuy.domain.menu.provider;

import sharebuy.domain.menu.domain.TopNavComponent;
import sharebuy.domain.menu.entity.Menu;
import sharebuy.domain.user.entity.User;

public sealed interface TopNavProvider permits LocationInfoProvider,AlarmProvider,MenuProvider{
    TopNavComponent getType();

    Object getValue(User user, Menu menu);
}
